package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.Admin
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao
import java.sql.Connection

/**
 * Responsible for database login operations
 * @author Simpli© CLI generator
 */
class LoginServiceDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {

    fun getIdOfAdmin(email: String?, password: String?): Long {
        return nullToZero(selectFirstLong("""
                SELECT idAdminPk
                FROM admin
                WHERE email = ?
                AND password = sha2(?, 256)
                """,
                email, password))
    }

    fun getAdmin(idAdminPk: Long): Admin? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM admin
            WHERE idAdminPk = ?
            """, { rs -> Admin.buildAll(rs) }, idAdminPk)
    }

    fun getUserByEmail(email: String?): Admin? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM admin
            WHERE email = ?
            """, { rs -> Admin.buildAll(rs)}, email)
    }

    fun updateUserPassword(email: String, password: String): Int {
        //TODO: review generated method
        return update("""
            UPDATE admin
            SET password = IF(? IS NOT NULL, SHA2(?, 256), password)
            WHERE email = ?
            """, password, password, email).affectedRows
    }

}
