package org.neoneputxoindexer.admin.process

import br.com.simpli.model.EnglishLanguage
import br.com.simpli.model.RespException
import br.com.simpli.sql.DaoTest
import br.com.simpli.tools.SecurityUtils
import org.neoneputxoindexer.admin.response.LoginResp
import org.neoneputxoindexer.exception.HttpException
import java.sql.Connection
import java.sql.SQLException
import javax.naming.NamingException
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests the login service
 * @author Simpli© CLI generator
 */
class LoginServiceTest @Throws(NamingException::class, SQLException::class)
constructor() : DaoTest("jdbc/neoIndexerDS", "neoIndexer") {

    private val subject: LoginService
    private val token: String?

    init {
        val con = getConnection()
        val lang = EnglishLanguage()
        val clientVersion = "w1.0.0"
        subject = LoginService(con, lang, clientVersion)
        token = LoginService.loginToToken("test@test.com", SecurityUtils.sha256("tester"))
    }

    @Test(expected = HttpException::class)
    fun testAuthFail() {
        subject.auth(null)
    }

    @Test(expected = HttpException::class)
    fun testSignInNull() {
        subject.signIn(null, null)
    }

    @Test(expected = HttpException::class)
    fun testSignInFail() {
        subject.signIn("test@test.com", SecurityUtils.sha256("wrongpassword"))
    }

    @Test
    fun testSignInAdmin() {
        val result = subject.signIn("test@test.com", SecurityUtils.sha256("tester"))
        assertEquals(token,
                result.token)
    }

    @Test(expected = HttpException::class)
    fun testAllowAccessFail() {
        subject.allowAccess("Hey")
    }

    @Test
    fun testAllowAccess() {
        subject.allowAccess(token)
    }

    @Test
    fun testGetIdFail() {
        val result = subject.getId(null, null)
        assertEquals(result, 0)
    }

    @Test
    fun testGetId() {
        val result = subject.getId("test@test.com", SecurityUtils.sha256("tester"))
        assertNotEquals(result, 0)
    }

    @Test
    fun testTokenToLoginNull() {
        val result = LoginService.tokenToLogin(null)
        assertNull(result)
    }

    @Test
    fun testTokenToLoginWithTokenNotACoolJson() {
        var tokenOtherObject = SecurityUtils.encrypt("Hey", LoginService.CRIPTOGRAPHY_HASH)

        tokenOtherObject = SecurityUtils.encode(tokenOtherObject!!, "UTF-8")

        val result = LoginService.tokenToLogin(tokenOtherObject)
        assertNull(result)
    }

    @Test
    fun testLoginToToken() {
        val result = LoginService.loginToToken("test@test.com", SecurityUtils.sha256("tester"))
        assertEquals(token,
                result)
    }

    @Test
    fun testLoginSerialized() {
        val lh = LoginService.LoginSerialized("a", "1")
        assertEquals("a", lh.email)
        assertEquals("1", lh.password)
    }

    @Test
    fun testLoginInfo() {
        val lh = LoginService.LoginInfo(2, LoginService.LoginSerialized("a", "1"))
        assertNotNull(lh.loginSerialized)
        assertEquals(2, lh.id)
    }

}
