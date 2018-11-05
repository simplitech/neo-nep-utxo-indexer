package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.Transaction
import org.neoneputxoindexer.admin.response.TransactionResp
import org.neoneputxoindexer.dao.TransactionDao
import org.neoneputxoindexer.dao.BlockDao
import org.neoneputxoindexer.dao.TransactionTypeDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * Transaction business logic
 * @author Simpli© CLI generator
 */
class TransactionProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<Transaction> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = TransactionDao(con, lang)

        val listTransaction = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listTransaction)

        if (!Strings.isNullOrEmpty(query)) {
            dao.count(query)?.let {
                count -> resp.recordsTotal = count
            }
        } else {
            dao.count()?.let{
                count -> resp.recordsTotal = count
            }
        }

        return resp
    }

    fun getOne(idTransactionPk: Long?): TransactionResp {
        //TODO: review generated method
        val transactionDao = TransactionDao(con, lang)
        val blockDao = BlockDao(con, lang)
        val transactionTypeDao = TransactionTypeDao(con, lang)

        val resp = TransactionResp()

        if (idTransactionPk != null && idTransactionPk > 0L) {
            val transaction = transactionDao.getOne(idTransactionPk)
            resp.transaction = transaction
        }

        resp.allBlock = blockDao.list()
        resp.allTransactionType = transactionTypeDao.list()

        return resp
    }

    fun persist(transaction: Transaction): Long {
        //TODO: review generated method
        val dao = TransactionDao(con, lang)

        val idTransaction: Long
        if (transaction.idTransactionPk > 0) {
            transaction.validate(true, lang)
            idTransaction = transaction.idTransactionPk
            
            dao.updateTransaction(transaction)
        } else {
            transaction.validate(false, lang)
            idTransaction = dao.insert(transaction)
            transaction.idTransactionPk = idTransaction
        }

        return idTransaction
    }
}
