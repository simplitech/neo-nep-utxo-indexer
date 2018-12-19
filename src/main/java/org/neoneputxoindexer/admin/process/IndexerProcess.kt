package org.neoneputxoindexer.admin.process

import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import com.google.common.base.Strings
import com.google.gson.Gson
import java.net.URL
import java.sql.Connection
import org.neoneputxoindexer.dao.IndexerDao
import org.neoneputxoindexer.model.*



class IndexerProcess(private val con: Connection, private val lang: LanguageHolder) {

    val apiPrefix = "http://chain.simpli.com.br:30333?jsonrpc=2.0"
    val urlRpcGetBlock = apiPrefix.plus("&method=getblock&params=")
    val urlRpcGetApplicationLogs = apiPrefix.plus("&method=getapplicationlog&params=")
    val reversedScriptHash = "b43bc406d44c6db622b2dd2a5ee2cbc5672e4448";//Reverse your script hash here: neocompiler.io/#/ecolab/ ('conversors')
    var gson = Gson()

    fun rpcGetBlock(blockHeight : Int) : String
    {
        return urlRpcGetBlock.plus("[$blockHeight,1]&id=1")
    }

    fun rpcGetTransaction(txHash: String) : String
    {
        return urlRpcGetApplicationLogs.plus("[\"$txHash\",1]&id=1")
    }

    fun getBlock(block: Int) : BlockRpcResponse
    {
        val apiResponse = URL(rpcGetBlock(block)).readText()
        val notBase = gson.fromJson(apiResponse, BlockRpcResponse::class.java);
        return notBase;
    }

    fun getNotifications(txHash: String) : ApplicationLogsRpcResponse
    {
        val apiResponse = URL(rpcGetTransaction(txHash)).readText()
        val notBase = gson.fromJson(apiResponse, ApplicationLogsRpcResponse::class.java);
        return notBase;
    }

    fun handleNotification(notificationResponse: ApplicationLogsRpcResponse) : String
    {
        if(!notificationResponse.result.notifications.isEmpty())
        {
            val firstNotification = notificationResponse.result.notifications[0]
            if(!firstNotification.state.value.isEmpty())
            {
                val firstNotificationType = firstNotification.state.value[0]
                if(firstNotificationType.type == "ByteArray")
                {
                    val notificationName = LowLevelUtils.hex2str(firstNotificationType.value)

                    handleNotification(notificationName, firstNotification, notificationResponse.result.txid)
                    return notificationName
                }
            }
        }
        return ""
    }

    fun handleNotification(notificationName : String, notification: Notification, transactionHash: String)
    {
        val indexerDao = IndexerDao(con, lang)
        if (!indexerDao.hasTransaction(transactionHash))
        {
            when (notificationName) {
                "newRegularAccount" -> {
                    val accountName = notification.state.value[1]
                    indexerDao.insertNewRegularAccount(accountName.value)
                }
                "mint" -> {
                    val masterAccount = notification.state.value[1]
                    val amount = notification.state.value[2]
                    val amountValue = LowLevelUtils.hex2Int(amount.value)
                    val recipient = notification.state.value[3]
                    val txHash = notification.state.value[4]
                    indexerDao.insertMint(masterAccount.value, amountValue, recipient.value, txHash.value)
                }
                "newMasterAccount" -> {
                    val masterAccount = notification.state.value[1]
                    indexerDao.insertMasterAccount(masterAccount.value)
                }
                "regularAccountApproved" -> {
                    val masterAccount = notification.state.value[1]
                    val regularAccount = notification.state.value[2]
                    indexerDao.insertRegularAccountApproval(masterAccount.value, regularAccount.value)
                }
                "transferTransactions" -> {
                    val sender = notification.state.value[1]
                    val recipient = notification.state.value[2]
                    val txHash = notification.state.value[3]

                }
            }
            indexerDao.insertTransaction(transactionHash)
        }
    }


    fun checkIfBlockHasTransactions(block: Int) : Boolean
    {
        val block = getBlock(block);
        block.result?.let{
            for (transaction in it.tx)
            {
                if (transaction.type == "InvocationTransaction")
                {
                    if (transaction.script.endsWith(reversedScriptHash))
                    {
                        return true
                    }
                }
            }
        }
        return false
    }


    fun listTransferTransactions(
            queryP: String?,
            page: Int?,
            limit: Int?,
            orderRequest: String?,
            startDate: java.util.Date?,
            endDate: java.util.Date?,
            asc: Boolean?): PagedResp<TransferTransaction> {
        var query = queryP

        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = IndexerDao(con, lang)

        val listTransferTransactions : List<TransferTransaction> = dao.listTransferTransactions(startDate, endDate, query, page, limit, orderRequest, asc)

        val resp = PagedResp(listTransferTransactions)

        if (!Strings.isNullOrEmpty(query)) {
            dao.countTransferTransactions(startDate, endDate, query)?.let {
                count -> resp.recordsTotal = count
            }
        } else {
            dao.countTransferTransactions(startDate, endDate)?.let{
                count -> resp.recordsTotal = count
            }
        }

        return resp
    }



}