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

    fun handleNotification(notificationResponse: ApplicationLogsRpcResponse, blockHeight: Int) : String
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

                    handleNotification(notificationName, firstNotification, notificationResponse.result.txid, blockHeight)
                    return notificationName
                }
            }
        }

        return ""
    }

    fun indexBlock(blockHeight: Int)
    {
        val blockResponse = getBlock(blockHeight)
        val indexerDao = IndexerDao(con, lang)
        if(!indexerDao.hasBlock(blockHeight)){
            indexerDao.insertBlock(blockHeight)
        }
        for(transaction in blockResponse.result.tx)
        {
            val notificationResp = getNotifications(transaction.txid)
            handleNotification(notificationResp, blockHeight)
        }

    }

    fun handleNotification(notificationName : String, notification: Notification, transactionHash: String, blockHeight: Int)
    {

        val indexerDao = IndexerDao(con, lang)

        if (!indexerDao.hasTransaction(transactionHash))
        {
            when (notificationName) {
                "newRegularAccount" -> {
                    val accountName = notification.state.value[1]
                    indexerDao.insertNewRegularAccount(accountName.value, blockHeight)
                }
                "mint" -> {
                    val masterAccount = notification.state.value[1].value
                    val amount = notification.state.value[2].value
                    val amountValue = LowLevelUtils.hex2Int(amount)
                    val recipient = notification.state.value[3].value
                    val txHash = notification.state.value[4].value
                    indexerDao.insertMint(masterAccount, amountValue, recipient, txHash, blockHeight)
                }
                "newMasterAccount" -> {
                    val masterAccount = notification.state.value[1].value
                    indexerDao.insertMasterAccount(masterAccount, blockHeight)
                }
                "regularAccountApproved" -> {
                    val masterAccount = notification.state.value[1].value
                    val regularAccount = notification.state.value[2].value
                    indexerDao.insertRegularAccountApproval(masterAccount, regularAccount, blockHeight)
                }
                "transferTransactions" -> {
                    val from = notification.state.value[1].value
                    val to = notification.state.value[2].value
                    val amount = notification.state.value[3].value
                    val amountValue = LowLevelUtils.hex2Int(amount)
                    val txHash = notification.state.value[4].value
                    val changeAmount = notification.state.value[5].value
                    var changeValue = LowLevelUtils.hex2Int(changeAmount)

                    var changeTxHash  = ""
                    if(notification.state.value.size > 6) {
                        changeTxHash = notification.state.value[6].value
                    }
                    indexerDao.insertTransferTransaction(from, to, amountValue, txHash, changeValue, changeTxHash, blockHeight)
                }
            }
            indexerDao.insertTransaction(transactionHash, blockHeight)
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