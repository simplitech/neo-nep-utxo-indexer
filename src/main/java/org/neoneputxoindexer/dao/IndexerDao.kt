package org.neoneputxoindexer.dao

import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao
import com.google.common.base.Strings
import org.neoneputxoindexer.model.TransferTransaction
import java.sql.Connection
import java.util.*

class IndexerDao (con: Connection, lang: LanguageHolder) : Dao(con, lang){
    fun hasTransaction(transactionHash: String): Boolean {
        return  exist("SELECT invocationTransactionHash FROM transactions WHERE invocationTransactionHash = ?", transactionHash)
    }

    fun insertNewRegularAccount(scriptHash: String, block: Int) {
       update("INSERT INTO account_registration_history (accountAddress, idBlockFk) VALUES (?, ?)", scriptHash, block)
    }

    fun insertTransaction(transactionHash: String, block: Int) {
        update("INSERT INTO transactions (invocationTransactionHash, idBlockFk) VALUES (?, ?)", transactionHash, block)
    }

    fun insertMint(masterAccount: String, mintAmount: Int, recipient: String, txHash: String, block: Int) {
        update("INSERT INTO transaction_mint_history (transactionHash, masterAccount, amount, recipient, idBlockFk) VALUES (?,?,?,?,?)",
                txHash, masterAccount, mintAmount, recipient, block)
    }

    fun insertMasterAccount(masterAccount: String, block: Int) {
        update("INSERT INTO master_registration_history (masterAccount, idBlockFk) VALUES (?, ?)", masterAccount, block)
    }

    fun insertRegularAccountApproval(masterAccount: String, regularAccount: String, block: Int) {
        update("INSERT INTO account_approvals_history  (masterAccount, regularAccount, idBlockFk) VALUES (?, ?, ?)", masterAccount, regularAccount, block)
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

    fun insertTransferTransaction(from: String, to: String, amountValue: Int, txHash: String, changeValue: Int, changeTxHash: String, blockHeight: Int) {
        update("INSERT INTO transfer_history (transactionHash, sender, recipient, amount, changeAmount, changeTransactionHash, idBlockFk) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)", txHash, from, to, amountValue, changeValue, changeTxHash, blockHeight)
    }

    fun hasBlock(blockHeight: Int): Boolean {
        return  exist("SELECT idBlockPk FROM block WHERE height = ?", blockHeight)
    }

    fun insertBlock(blockHeight: Int) {
        update("INSERT INTO block (height, dateTime) VALUES (?, NOW())", blockHeight)
    }
}