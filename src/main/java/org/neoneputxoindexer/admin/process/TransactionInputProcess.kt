package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.TransactionInput
import org.neoneputxoindexer.admin.response.TransactionInputResp
import org.neoneputxoindexer.dao.TransactionInputDao
import org.neoneputxoindexer.dao.AssetDao
import org.neoneputxoindexer.dao.TransactionDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * TransactionInput business logic
 * @author Simpli© CLI generator
 */
class TransactionInputProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<TransactionInput> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = TransactionInputDao(con, lang)

        val listTransactionInput = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listTransactionInput)

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

    fun getOne(idTransactionInputPk: Long?): TransactionInputResp {
        //TODO: review generated method
        val transactionInputDao = TransactionInputDao(con, lang)
        val assetDao = AssetDao(con, lang)
        val transactionDao = TransactionDao(con, lang)

        val resp = TransactionInputResp()

        if (idTransactionInputPk != null && idTransactionInputPk > 0L) {
            val transactionInput = transactionInputDao.getOne(idTransactionInputPk)
            resp.transactionInput = transactionInput
        }

        resp.allAsset = assetDao.list()
        resp.allTransaction = transactionDao.list()

        return resp
    }

    fun persist(transactionInput: TransactionInput): Long {
        //TODO: review generated method
        val dao = TransactionInputDao(con, lang)

        val idTransactionInput: Long
        if (transactionInput.idTransactionInputPk > 0) {
            transactionInput.validate(true, lang)
            idTransactionInput = transactionInput.idTransactionInputPk
            
            dao.updateTransactionInput(transactionInput)
        } else {
            transactionInput.validate(false, lang)
            idTransactionInput = dao.insert(transactionInput)
            transactionInput.idTransactionInputPk = idTransactionInput
        }

        return idTransactionInput
    }
}
