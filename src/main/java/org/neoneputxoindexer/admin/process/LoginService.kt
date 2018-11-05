package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.dao.LoginServiceDao
import com.google.gson.Gson
import java.util.*
import java.sql.Connection
import org.neoneputxoindexer.admin.mail.ResetPasswordMail
import org.neoneputxoindexer.admin.response.LoginResp
import org.neoneputxoindexer.exception.HttpException
import org.neoneputxoindexer.model.Admin
import br.com.simpli.model.LanguageHolder
import br.com.simpli.tools.SecurityUtils
import javax.ws.rs.core.Response

/**
 * User Login Services
 * @author Simpli© CLI generator
 */
class LoginService(private val con: Connection, private val lang: LanguageHolder, private val clientVersion: String) {

    fun auth(token: String?): LoginResp {
        val lHolder: LoginSerialized? = tokenToLogin(token)
        val email: String? = lHolder?.email
        val password: String? = lHolder?.password
        val id = getId(email, password)
        val admin = getAdmin(id)

        if (id == 0L) {
            throw HttpException(lang.invalidLogin(), Response.Status.UNAUTHORIZED)
        }

        return LoginResp(token, id, admin)
    }

    fun signIn(email: String?, password: String?): LoginResp {
        val token = loginToToken(email, password)
        val id = getId(email, password)
        val admin = getAdmin(id)

        if (id == 0L) {
            throw HttpException(lang.invalidLogin(), Response.Status.UNAUTHORIZED)
        }

        return LoginResp(token, id, admin)
    }

    fun resetPassword(email: String?): Long {
        val dao = LoginServiceDao(con, lang)

        if (email === null) {
            throw HttpException(lang.cannotBeNull("Email"), Response.Status.NOT_ACCEPTABLE)
        }

        val user = dao.getUserByEmail(email)

        if (user === null || user.email === null) {
            throw HttpException(lang.emailNotFound(), Response.Status.NOT_ACCEPTABLE)
        }

        var hash = Gson().toJson(TokenForgottenPassword(user.email as String))
        hash = SecurityUtils.encrypt(hash, LoginService.CRIPTOGRAPHY_HASH)
        hash = hash.replace("/", "%2F")

        ResetPasswordMail("http://localhost:8181", lang, user, hash).send()

        return 1L
    }

    fun recoverPassword(password: String?, hash: String?): String? {
        val dao = LoginServiceDao(con, lang)

        if (password.isNullOrEmpty()) {
            throw HttpException(lang.cannotBeNull("Password"), Response.Status.NOT_ACCEPTABLE)
        }

        if (hash.isNullOrEmpty()) {
            throw HttpException(lang.cannotBeNull("Hash"), Response.Status.NOT_ACCEPTABLE)
        }

        val hashResolved = hash!!.replace(" ", "+")
        val token = SecurityUtils.decrypt(hashResolved, LoginService.CRIPTOGRAPHY_HASH)

        if (token === null) {
            throw HttpException(lang.invalidToken(), Response.Status.NOT_ACCEPTABLE)
        }

        val objToken = Gson().fromJson<TokenForgottenPassword>(token, TokenForgottenPassword::class.java)

        if (objToken === null) {
            throw HttpException(lang.expiredToken(), Response.Status.NOT_ACCEPTABLE)
        }

        val calendar = Calendar.getInstance()
        calendar.time = objToken.date
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        val dataExpiracao = calendar.time

        //token expires after 15 days
        if (dataExpiracao.before(Date())) {
            throw HttpException(lang.expiredToken(), Response.Status.NOT_ACCEPTABLE)
        }

        dao.updateUserPassword(objToken.email, password!!)

        return loginToToken(objToken.email, password)
    }

    fun getId(email: String?, password: String?): Long {
        val dao = LoginServiceDao(con, lang)

        return dao.getIdOfAdmin(email, password)
    }

    fun getAdmin(id: Long): Admin? {
        val dao = LoginServiceDao(con, lang)

        return dao.getAdmin(id)
    }

    fun allowAccess(token: String?): LoginInfo {
        val login = tokenToLogin(token) ?: throw HttpException(lang.pleaseLogin(), Response.Status.UNAUTHORIZED)

        val id = getId(login.email, login.password)

        if (id == 0L) {
            throw HttpException(lang.pleaseLogin(), Response.Status.UNAUTHORIZED)
        }

        return LoginInfo(id, login)
    }

    class LoginSerialized(val email: String?, val password: String?, val hash: String? = "")

    class LoginInfo {
        val id: Long
        val loginSerialized: LoginSerialized

        constructor(id: Long, loginSerialized: LoginSerialized) {
            this.loginSerialized = loginSerialized
            this.id = id
        }

        constructor(id: Long, email: String?, password: String?, hash: String? = "") {
            this.loginSerialized = LoginSerialized(email, password, hash)
            this.id = id
        }
    }

    class TokenForgottenPassword (val email: String, val date: Date? = Date())

    companion object {

        fun loginToToken(email: String?, password: String?): String? {
            var token = Gson().toJson(LoginSerialized(email, password))
            token = SecurityUtils.encrypt(token, CRIPTOGRAPHY_HASH)

            token = SecurityUtils.encode(token, "UTF-8")

            return token
        }

        fun tokenToLogin(tokenP: String?): LoginSerialized? {
            var token: String? = tokenP ?: return null

            token = SecurityUtils.decode(token ?: "", "UTF-8")
            token = SecurityUtils.decrypt(token ?: "", CRIPTOGRAPHY_HASH)

            if (token == null) {
                return null
            }

            try {
                return Gson().fromJson(token, LoginSerialized::class.java)
            } catch (e: Exception) {
                return null
            }
        }

        val CRIPTOGRAPHY_HASH = "01e0276e-b1e2-4975-bf5f-73e5d4d292c8"
    }
}
