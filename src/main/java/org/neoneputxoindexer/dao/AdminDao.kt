package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.Admin
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for Admin database operations
 * @author Simpli© CLI generator
 */
class AdminDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idAdminPk: Long): Admin? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM admin
            WHERE 1 = 1
            AND idAdminPk = ?
            """, { rs -> Admin.buildAll(rs) }, idAdminPk)
    }

    fun list(): MutableList<Admin> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM admin
            """, { rs -> Admin.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<Admin> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idAdminPk", "admin.idAdminPk")
        orderRequestAndColumn.put("email", "admin.email")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(admin.idAdminPk, ''),
                IFNULL(admin.email, '')
                )) LIKE LOWER(?)
                """)
            params.add("%$query%")
        }

        var limitQuery = ""
        if (page != null && limit != null) {
            limitQuery = "LIMIT ?, ? "
            params.add(page * limit)
            params.add(limit)
        }

        return selectList("""
            SELECT *
            FROM admin
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> Admin.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idAdminPk)
            FROM admin
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idAdminPk)
            FROM admin
            WHERE LOWER(CONCAT(
                IFNULL(admin.idAdminPk, ''),
                IFNULL(admin.email, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateAdmin(admin: Admin): Int {
        //TODO: review generated method
        return update("""
            UPDATE admin
            SET
            email = ?,
            password = IF(? IS NOT NULL, SHA2(?, 256), password)
            WHERE 1 = 1
            AND idAdminPk = ?
            """,
            admin.email,
            admin.password, admin.password,
            admin.idAdminPk
        ).affectedRows
    }

    fun insert(admin: Admin): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO admin (
            email,
            password
            ) VALUES (?,SHA2(?, 256))
            """,
            admin.email,
            admin.password
        ).key
    }

    fun existAdmin(idAdminPk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idAdminPk
            FROM admin
            WHERE 1 = 1
            AND idAdminPk = ?
            """, idAdminPk)
    }

}
