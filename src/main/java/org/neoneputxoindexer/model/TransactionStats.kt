package org.neoneputxoindexer.model

import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class TransactionStats
{
    var date = Date()
    var amountSum = 0 // by day
    var transactionsCount = 0 // by day
    var mintTokensCount = 0 // by day
    var regularAccountsCount = 0 // by day
    var approvedAccountsCount = 0 // by day

    companion object {
        @Throws(SQLException::class)
        @JvmOverloads
        fun buildAll(rs: ResultSet): TransactionStats {
            val transferTransaction = TransactionStats()

            transferTransaction.date = rs.getDate("date")
            transferTransaction.transactionsCount = rs.getInt("transactionsCount")
            transferTransaction.mintTokensCount = rs.getInt("mintTokensCount")
            transferTransaction.amountSum = rs.getInt("amountSum")
            transferTransaction.regularAccountsCount = rs.getInt("regularAccountsCount")
            transferTransaction.approvedAccountsCount = rs.getInt("approvedAccountsCount")
            return transferTransaction
        }
    }


}
