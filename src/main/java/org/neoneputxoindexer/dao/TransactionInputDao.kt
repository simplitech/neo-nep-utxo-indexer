package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.TransactionInput
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for TransactionInput database operations
 * @author Simpli© CLI generator
 */
class TransactionInputDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idTransactionInputPk: Long): TransactionInput? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM transaction_input
            WHERE 1 = 1
            AND idTransactionInputPk = ?
            """, { rs -> TransactionInput.buildAll(rs) }, idTransactionInputPk)
    }

    fun list(): MutableList<TransactionInput> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM transaction_input
            """, { rs -> TransactionInput.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<TransactionInput> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idTransactionInputPk", "transaction_input.idTransactionInputPk")
        orderRequestAndColumn.put("previousIdTransactionFk", "transaction_input.previousIdTransactionFk")
        orderRequestAndColumn.put("idTransactionFk", "transaction_input.idTransactionFk")
        orderRequestAndColumn.put("amount", "transaction_input.amount")
        orderRequestAndColumn.put("spent", "transaction_input.spent")
        orderRequestAndColumn.put("to", "transaction_input.to")
        orderRequestAndColumn.put("idAssetFk", "transaction_input.idAssetFk")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(transaction_input.idTransactionInputPk, ''),
                IFNULL(transaction_input.previousIdTransactionFk, ''),
                IFNULL(transaction_input.idTransactionFk, ''),
                IFNULL(transaction_input.amount, ''),
                IFNULL(transaction_input.spent, ''),
                IFNULL(transaction_input.to, ''),
                IFNULL(transaction_input.idAssetFk, '')
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
            FROM transaction_input
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> TransactionInput.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionInputPk)
            FROM transaction_input
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idTransactionInputPk)
            FROM transaction_input
            WHERE LOWER(CONCAT(
            IFNULL(transaction_input.idTransactionInputPk, ''),
            IFNULL(transaction_input.previousIdTransactionFk, ''),
            IFNULL(transaction_input.idTransactionFk, ''),
            IFNULL(transaction_input.amount, ''),
            IFNULL(transaction_input.spent, ''),
            IFNULL(transaction_input.to, ''),
            IFNULL(transaction_input.idAssetFk, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateTransactionInput(transactionInput: TransactionInput): Int {
        //TODO: review generated method
        return update("""
            UPDATE transaction_input
            SET
            previousIdTransactionFk = ?,
            idTransactionFk = ?,
            amount = ?,
            spent = ?,
            to = ?,
            idAssetFk = ?
            WHERE 1 = 1
            AND idTransactionInputPk = ?
            """,
            transactionInput.previousIdTransactionFk,
            transactionInput.idTransactionFk,
            transactionInput.amount,
            transactionInput.spent,
            transactionInput.to,
            transactionInput.idAssetFk,
            transactionInput.idTransactionInputPk
        ).affectedRows
    }

    fun insert(transactionInput: TransactionInput): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO transaction_input (
            previousIdTransactionFk,
            idTransactionFk,
            amount,
            spent,
            to,
            idAssetFk
            ) VALUES (?,?,?,?,?,?)
            """,
            transactionInput.previousIdTransactionFk,
            transactionInput.idTransactionFk,
            transactionInput.amount,
            transactionInput.spent,
            transactionInput.to,
            transactionInput.idAssetFk
        ).key
    }

    fun existTransactionInput(idTransactionInputPk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idTransactionInputPk
            FROM transaction_input
            WHERE 1 = 1
            AND idTransactionInputPk = ?
            """, idTransactionInputPk)
    }

}
