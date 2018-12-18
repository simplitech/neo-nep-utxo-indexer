package org.neoneputxoindexer

import br.com.simpli.model.EnglishLanguage
import br.com.simpli.sql.DaoTest
import org.junit.Assert
import org.junit.Test
import org.neoneputxoindexer.admin.process.IndexerProcess
import java.sql.Connection

/**
 * Test other services
 * @author Simpli© CLI generator
 */
class IndexerTest : DaoTest("jdbc/neoIndexerDS", "neoIndexer") {

    private val con: Connection
    private val subject : IndexerProcess;

    init {
        con = getConnection()
        val lang = EnglishLanguage()
        subject = IndexerProcess(con, lang)
    }

    @Test
    fun  testIsOurAppCall()
    {
        val hasTransactions = subject.checkIfBlockHasTransactions(4957);
        Assert.assertTrue(hasTransactions)
        print(hasTransactions)
    }

    @Test
    fun testGetNotifications()
    {
        val notifications = subject.getNotifications("ef8aa846da0bc05161aa9f6894e7bc8949968f8b5574d02d7cd4a636386a88a9");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications)
        con.commit()
        print(notType)
    }

//    constructor() : DaoTest("jdbc/neoIndexerDS", "neoIndexer") {
//
//
//        @Test
//        fun testIndexMint() {
//
//        }
//
//    }
}
