package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.TransactionType
import org.neoneputxoindexer.admin.response.TransactionTypeResp
import org.neoneputxoindexer.dao.TransactionTypeDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * TransactionType business logic
 * @author Simpli© CLI generator
 */
class TransactionTypeProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<TransactionType> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = TransactionTypeDao(con, lang)

        val listTransactionType = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listTransactionType)

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

    fun getOne(idTransactionTypePk: Long?): TransactionTypeResp {
        //TODO: review generated method
        val transactionTypeDao = TransactionTypeDao(con, lang)

        val resp = TransactionTypeResp()

        if (idTransactionTypePk != null && idTransactionTypePk > 0L) {
            val transactionType = transactionTypeDao.getOne(idTransactionTypePk)
            resp.transactionType = transactionType
        }


        return resp
    }

    fun persist(transactionType: TransactionType): Long {
        //TODO: review generated method
        val dao = TransactionTypeDao(con, lang)

        val idTransactionType: Long
        if (transactionType.idTransactionTypePk > 0) {
            transactionType.validate(true, lang)
            idTransactionType = transactionType.idTransactionTypePk
            
            dao.updateTransactionType(transactionType)
        } else {
            transactionType.validate(false, lang)
            idTransactionType = dao.insert(transactionType)
            transactionType.idTransactionTypePk = idTransactionType
        }

        return idTransactionType
    }
}
