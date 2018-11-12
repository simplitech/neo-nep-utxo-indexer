package org.neoneputxoindexer.admin.process

import br.com.simpli.model.LanguageHolder
import com.google.gson.Gson
import org.neoneputxoindexer.model.Nep5Notification
import org.neoneputxoindexer.model.NotificationBase
import java.net.URL
import java.sql.Connection

class IndexerProcess(private val con: Connection, private val lang: LanguageHolder) {

    val apiPrefix : String = "http://chain.simpli.com.br:8080/v1"
    val apiNotificationPerBlock : String = apiPrefix.plus("/notifications/contract/")
    var gson = Gson()

    fun getNotificationsNep5NotificationsFromBlock(contractAddress: String): NotificationBase {
        val queryResult = URL(apiNotificationPerBlock.plus(contractAddress)).readText()
        val json = gson.fromJson(queryResult, NotificationBase::class.java)
        return  json;
    }


}