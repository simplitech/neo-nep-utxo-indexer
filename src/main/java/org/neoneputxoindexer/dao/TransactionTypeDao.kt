package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.TransactionType
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for TransactionType database operations
 * @author Simpli© CLI generator
 */
class TransactionTypeDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idTransactionTypePk: Long): TransactionType? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM transaction_type
            WHERE 1 = 1
            AND idTransactionTypePk = ?
            """, { rs -> TransactionType.buildAll(rs) }, idTransactionTypePk)
    }

    fun list(): MutableList<TransactionType> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM transaction_type
            """, { rs -> TransactionType.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<TransactionType> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idTransactionTypePk", "transaction_type.idTransactionTypePk")
        orderRequestAndColumn.put("name", "transaction_type.name")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(transaction_type.idTransactionTypePk, ''),
                IFNULL(transaction_type.name, '')
                )) LIKE LOWER(?)
                """)
            params.add("%$query%")
        }

        var limitQuery = ""
        if (page != null && limit != null) {
            limitQuery = "LIMIT ?, ? "
            params.add(page * limit)
            params.add(limit)
        }

        return selectList("""
            SELECT *
            FROM transaction_type
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> TransactionType.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionTypePk)
            FROM transaction_type
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionTypePk)
            FROM transaction_type
            WHERE LOWER(CONCAT(
            IFNULL(transaction_type.idTransactionTypePk, ''),
            IFNULL(transaction_type.name, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateTransactionType(transactionType: TransactionType): Int {
        //TODO: review generated method
        return update("""
            UPDATE transaction_type
            SET
            name = ?
            WHERE 1 = 1
            AND idTransactionTypePk = ?
            """,
            transactionType.name,
            transactionType.idTransactionTypePk
        ).affectedRows
    }

    fun insert(transactionType: TransactionType): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO transaction_type (
            name
            ) VALUES (?)
            """,
            transactionType.name
        ).key
    }

    fun existTransactionType(idTransactionTypePk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idTransactionTypePk
            FROM transaction_type
            WHERE 1 = 1
            AND idTransactionTypePk = ?
            """, idTransactionTypePk)
    }

}
