package org.neoneputxoindexer.model

import br.com.simpli.sql.getInt
import br.com.simpli.sql.getString

import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class TransferTransaction
{
    var transactionHash = ""
    var sender = ""
    var recipient = ""
    var change = 0
    var changeTransactionHash = ""
    var blockDate = Date()

    companion object {
        @Throws(SQLException::class)
        @JvmOverloads
        fun buildAll(rs: ResultSet, alias: String = "transfer_history"): TransferTransaction {
            val transferTransaction = TransferTransaction()

            transferTransaction.transactionHash = rs.getString(alias,"transactionHash").toString()
            transferTransaction.sender = rs.getString(alias,"sender").toString()
            transferTransaction.recipient = rs.getString(alias,"recipient").toString()
            transferTransaction.change = rs.getInt(alias,"changeAmount")
            transferTransaction.changeTransactionHash = rs.getString(alias,"transactionHash").toString()
            transferTransaction.blockDate = rs.getDate("dateTime")
            return transferTransaction
        }
    }


}
