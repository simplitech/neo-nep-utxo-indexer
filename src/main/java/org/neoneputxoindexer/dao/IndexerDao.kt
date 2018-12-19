package org.neoneputxoindexer.dao

import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao
import com.google.common.base.Strings
import org.neoneputxoindexer.model.TransferTransaction
import java.sql.Connection
import java.util.*

class IndexerDao (con: Connection, lang: LanguageHolder) : Dao(con, lang){
    fun hasTransaction(transactionHash: String): Boolean {
        return  exist("SELECT invocationTransactionHash FROM transaction WHERE invocationTransactionHash = ?", transactionHash)
    }

    fun insertNewRegularAccount(scriptHash: String) {
       update("INSERT INTO account_registration_history (accountAddress, registrationDate) VALUES (?, NOW())", scriptHash)
    }

    fun insertTransaction(transactionHash: String) {
        update("INSERT INTO transaction (invocationTransactionHash) VALUES (?)", transactionHash)
    }

    fun insertMint(masterAccount: String, mintAmount: Int, recipient: String, txHash: String) {
        update("INSERT INTO transaction_mint_history (transactionHash, masterAccount, amount, recipient, dateTime) VALUES (?,?,?,?,NOW())",
                txHash, masterAccount, mintAmount, recipient)
    }

    fun insertMasterAccount(masterAccount: String) {
        update("INSERT INTO master_registration_history (masterAccount, dateTime) VALUES (?, NOW())", masterAccount)
    }

    fun insertRegularAccountApproval(masterAccount: String, regularAccount: String) {
        update("INSERT INTO account_approvals_history  (masterAccount, regularAccount, dateTime) VALUES (?, ?, NOW())", masterAccount, regularAccount)
    }


    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idAdminPk)
            FROM admin
            """)
    }

    fun countTransferTransactions(startDate: Date?, endDate: Date?): Int? {
        return selectFirstInt("""
            SELECT COUNT(transfer_history.idTransferPk)
            FROM transfer_history INNER JOIN block ON block.idBlockPk = transfer_history.idBlockFk
            WHERE dateTime >= ? AND dateTime <= ?
                """,
                startDate, endDate)
    }

    fun countTransferTransactions(startDate: Date?, endDate: Date?, search: String?): Int? {
        return selectFirstInt("""
            SELECT COUNT(transfer_history.idTransferPk)
            FROM transfer_history INNER JOIN block ON block.idBlockPk = transfer_history.idBlockFk
            WHERE
            dateTime >= ? AND dateTime <= ?,
            AND LOWER(CONCAT(
                IFNULL(transfer_history.transactionHash, ''),
                IFNULL(transfer_history.recipient, ''),
                IFNULL(transfer_history.sender, ''),
                IFNULL(transfer_history.changeTransactionHash, '')
                )) LIKE LOWER(?)
                """,
                startDate, endDate, "%$search%")
    }


    fun listTransferTransactions(startDate: Date?, endDate: Date?, query: String?, page: Int?, limit: Int?, orderRequest: String?, asc: Boolean?): List<TransferTransaction> {
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("dateTime", "block.dateTime")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND dateTime >= ? AND dateTime <= ?
                AND LOWER(CONCAT(
                IFNULL(transfer_history.transactionHash, ''),
                IFNULL(transfer_history.recipient, ''),
                IFNULL(transfer_history.sender, ''),
                IFNULL(transfer_history.changeTransactionHash, '')
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
            FROM transfer_history INNER JOIN block ON block.idBlockPk = transfer_history.idBlockFk
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> TransferTransaction.buildAll(rs) }, *params.toTypedArray())
    }
}