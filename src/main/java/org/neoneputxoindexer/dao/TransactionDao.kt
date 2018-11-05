package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.Transaction
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for Transaction database operations
 * @author Simpli© CLI generator
 */
class TransactionDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idTransactionPk: Long): Transaction? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM transaction
            WHERE 1 = 1
            AND idTransactionPk = ?
            """, { rs -> Transaction.buildAll(rs) }, idTransactionPk)
    }

    fun list(): MutableList<Transaction> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM transaction
            """, { rs -> Transaction.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<Transaction> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idTransactionPk", "transaction.idTransactionPk")
        orderRequestAndColumn.put("hash", "transaction.hash")
        orderRequestAndColumn.put("idTypeFk", "transaction.idTypeFk")
        orderRequestAndColumn.put("from", "transaction.from")
        orderRequestAndColumn.put("idBlockFk", "transaction.idBlockFk")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(transaction.idTransactionPk, ''),
                IFNULL(transaction.hash, ''),
                IFNULL(transaction.idTypeFk, ''),
                IFNULL(transaction.from, ''),
                IFNULL(transaction.idBlockFk, '')
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
            FROM transaction
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> Transaction.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionPk)
            FROM transaction
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionPk)
            FROM transaction
            WHERE LOWER(CONCAT(
            IFNULL(transaction.idTransactionPk, ''),
            IFNULL(transaction.hash, ''),
            IFNULL(transaction.idTypeFk, ''),
            IFNULL(transaction.from, ''),
            IFNULL(transaction.idBlockFk, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateTransaction(transaction: Transaction): Int {
        //TODO: review generated method
        return update("""
            UPDATE transaction
            SET
            hash = ?,
            idTypeFk = ?,
            from = ?,
            idBlockFk = ?
            WHERE 1 = 1
            AND idTransactionPk = ?
            """,
            transaction.hash,
            transaction.idTypeFk,
            transaction.from,
            transaction.idBlockFk,
            transaction.idTransactionPk
        ).affectedRows
    }

    fun insert(transaction: Transaction): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO transaction (
            hash,
            idTypeFk,
            from,
            idBlockFk
            ) VALUES (?,?,?,?)
            """,
            transaction.hash,
            transaction.idTypeFk,
            transaction.from,
            transaction.idBlockFk
        ).key
    }

    fun existTransaction(idTransactionPk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idTransactionPk
            FROM transaction
            WHERE 1 = 1
            AND idTransactionPk = ?
            """, idTransactionPk)
    }

}
