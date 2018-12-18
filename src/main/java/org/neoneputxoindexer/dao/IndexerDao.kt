package org.neoneputxoindexer.dao

import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao
import java.sql.Connection

class IndexerDao (con: Connection, lang: LanguageHolder) : Dao(con, lang){
    fun hasTransaction(transactionHash: String): Boolean {
        return  exist("SELECT transactionHash FROM transactions WHERE transactionHash = ?", transactionHash)
    }

    fun insertNewRegularAccount(scriptHash: String) {
       update("INSERT INTO account_registration_history (accountAddress, registrationDate) VALUES (?, NOW())", scriptHash)
    }

    fun insertTransaction(transactionHash: String) {
        update("INSERT INTO transactions (transactionHash) VALUES (?)", transactionHash)
    }

    fun insertMint(masterAccount: String, mintAmount: Int, recipient: String, txHash: String) {
        update("INSERT INTO transaction_mint_history (transactionHash, masterAccount, amount, recipient, date) VALUES (?,?,?,?,NOW())",
                txHash, masterAccount, mintAmount, recipient)
    }
}