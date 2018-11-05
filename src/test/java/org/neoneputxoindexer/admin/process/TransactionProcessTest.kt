package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.Transaction
import org.neoneputxoindexer.admin.response.TransactionResp
import org.neoneputxoindexer.exception.HttpException
import br.com.simpli.model.EnglishLanguage
import br.com.simpli.model.RespException
import br.com.simpli.sql.Dao
import br.com.simpli.sql.DaoTest
import br.com.simpli.tools.SecurityUtils
import java.sql.Connection
import java.sql.SQLException
import javax.naming.NamingException
import java.util.ArrayList
import java.util.Date
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before

/**
 * Tests Transaction business logic
 * @author Simpli© CLI generator
 */
class TransactionProcessTest @Throws(NamingException::class, SQLException::class)
constructor() : DaoTest("jdbc/neoIndexerDS", "neoIndexer") {

    private val con: Connection
    private val subject: TransactionProcess

    init {
        con = getConnection()
        val lang = EnglishLanguage()
        val clientVersion = "w1.0.0"
        subject = TransactionProcess(con, lang)
    }

    @Test
    fun testListNoQuery() {
        val query: String? = null
        val page = 0
        val limit = 20
        val orderRequest: String? = null
        val asc: Boolean? = null
                
        val result = subject.list(query, page, limit, orderRequest, asc)
        assertNotNull(result)
        assertNotNull(result.list)
        assertNotEquals(result.recordsTotal.toLong(), 0)
        assertFalse(result.list.isEmpty())
        assertNotNull(result.list[0])
    }

    @Test
    fun testListWithQuery() {
        val query: String? = "1"
        val page = 0
        val limit = 20
        val orderRequest: String? = null
        val asc: Boolean? = null
                
        val result = subject.list(query, page, limit, orderRequest, asc)
        assertNotNull(result)
        assertNotNull(result.list)
        assertNotEquals(result.recordsTotal.toLong(), 0)
        assertFalse(result.list.isEmpty())
        assertNotNull(result.list[0])
    }

    @Test
    fun testGetOne() {
        val result = subject.getOne(1L)
        assertNotNull(result)
        assertNotNull(result.transaction)
        assertFalse(result.allBlock!!.isEmpty())
        assertNotNull(result.allBlock!![0])
        assertFalse(result.allTransactionType!!.isEmpty())
        assertNotNull(result.allTransactionType!![0])
    }

    @Test
    fun testPersist() {
        val transaction = Transaction()
        transaction.idTransactionPk = 1
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.from = "1"
        transaction.idBlockFk = 1

        val result = subject.persist(transaction)
        assertNotNull(result)
        assertTrue(result > 0)
    }

    @Test
    fun testPersistUpdating() {
        val transaction = Transaction()
        transaction.idTransactionPk = 1
        transaction.hash = "1"
        transaction.idTypeFk = 1
        transaction.from = "1"
        transaction.idBlockFk = 1

        val result = subject.persist(transaction)
        assertNotNull(result)
        assertTrue(result > 0)
    }
}
