package org.neoneputxoindexer.admin.process

import br.com.simpli.model.EnglishLanguage
import br.com.simpli.sql.DaoTest
import org.junit.Assert
import org.junit.Test
import java.sql.Connection
import java.sql.SQLException
import javax.naming.NamingException

class IndexerProcessTest @Throws(NamingException::class, SQLException::class)
constructor() : DaoTest("jdbc/neoIndexerDS", "neoIndexer") {

    private val con: Connection
    private val subject: IndexerProcess

    init {
        con = getConnection()
        val lang = EnglishLanguage()
        val clientVersion = "w1.0.0"
        subject = IndexerProcess(con, lang)
    }

    @Test
    fun testConnectToEndpoint() {
        val contractAddress = "0154d85d1308577863db522f67ec17d649900f10"
        val result = subject.getNotificationsNep5NotificationsFromBlock(contractAddress)
        Assert.assertNotNull(result)
        Assert.assertTrue(result.results.size > 0)
    }

}