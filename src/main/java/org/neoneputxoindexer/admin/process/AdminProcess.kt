package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.Admin
import org.neoneputxoindexer.admin.response.AdminResp
import org.neoneputxoindexer.dao.AdminDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * Admin business logic
 * @author Simpli© CLI generator
 */
class AdminProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<Admin> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = AdminDao(con, lang)

        val listAdmin = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listAdmin)

        if (!Strings.isNullOrEmpty(query)) {
            dao.count(query)?.let {
                count -> resp.recordsTotal = count
            }
        } else {
            dao.count()?.let{
                count -> resp.recordsTotal = count
            }
        }

        return resp
    }

    fun getOne(idAdminPk: Long?): AdminResp {
        //TODO: review generated method
        val adminDao = AdminDao(con, lang)

        val resp = AdminResp()

        if (idAdminPk != null && idAdminPk > 0L) {
            val admin = adminDao.getOne(idAdminPk)
            resp.admin = admin
        }


        return resp
    }

    fun persist(admin: Admin): Long {
        //TODO: review generated method
        val dao = AdminDao(con, lang)

        val idAdmin: Long
        if (admin.idAdminPk > 0) {
            admin.validate(true, lang)
            idAdmin = admin.idAdminPk
            
            dao.updateAdmin(admin)
        } else {
            admin.validate(false, lang)
            idAdmin = dao.insert(admin)
            admin.idAdminPk = idAdmin
        }

        return idAdmin
    }
}
