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
        subject.indexBlock(1)
    }

    @Test
    fun  testIsOurAppCall()
    {
        val hasTransactions = subject.checkIfBlockHasTransactions(4957);
        Assert.assertTrue(hasTransactions)
        print(hasTransactions)
    }

    @Test
    fun testGetMintNotification()
    {
        val notifications = subject.getNotifications("ef8aa846da0bc05161aa9f6894e7bc8949968f8b5574d02d7cd4a636386a88a9");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications, 1)
        Assert.assertEquals(notType, "mint")
    }

    @Test
    fun testGetRegisterRegularAccountNotification()
    {
        val notifications = subject.getNotifications("5c3714db1aa986045d364f2c6ea4a7e963291e22294b02e66b1b2ff9fc592ae8");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications, 1)
        Assert.assertEquals("newRegularAccount", notType)
    }

    @Test
    fun testGetApproveRegularAccountNotification()
    {
        val notifications = subject.getNotifications("fad65dada508195495027e1a73136e1f0ac5df612e1000aff74d1d55013bdd11");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications, 1)
        Assert.assertEquals("regularAccountApproved", notType)
    }


    @Test
    fun testGetRegisterMasterAccountNotification()
    {
        val notifications = subject.getNotifications("7558b3c66f0ff2eb9cd42bf1c943adc1b1b13fb56acb7db8d0f405c7f4060d87");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications, 1)
        Assert.assertEquals("newMasterAccount", notType)
    }

    @Test
    fun testGetTransferNotification()
    {
        val notifications = subject.getNotifications("76d958c3daffcb85bafe41508fc27df62bba8ec15776a1353a7b11d88badbc88");
        Assert.assertNotNull(notifications)
        val notType = subject.handleNotification(notifications, 1)
        Assert.assertEquals("transferTransactions", notType)

    }

    @Test
    fun testIndexBlock()
    {
        subject.indexBlock(11689)
        con.commit()
    }



}
