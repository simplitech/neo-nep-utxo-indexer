package org.neoneputxoindexer.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.sql.SQLException
import java.util.Date
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.RespException
import br.com.simpli.sql.*
import java.sql.ResultSet
import br.com.simpli.tools.Validator
import org.neoneputxoindexer.exception.HttpException
import javax.ws.rs.core.Response

/**
 * Reference model of table admin
 * @author Simpli© CLI generator
 */
@ApiModel
class Admin {

    @ApiModelProperty(required = true)
    var idAdminPk: Long = 0

    @ApiModelProperty(required = true)
    var email: String? = null

    @ApiModelProperty(required = true)
    var password: String? = null

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (email.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("Email"), Response.Status.NOT_ACCEPTABLE)
		}
		if (email?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("Email", 255), Response.Status.NOT_ACCEPTABLE)
		}
		if (email != null && !Validator.isEmail(email)) {
			throw HttpException(lang.isNotAValidEmail("Email"), Response.Status.NOT_ACCEPTABLE)
		}
		if (password.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("Password"), Response.Status.NOT_ACCEPTABLE)
		}
		if (password?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("Password", 255), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "admin"): Admin {
			val admin = Admin()

			admin.idAdminPk = rs.getLong(alias, "idAdminPk")
			admin.email = rs.getString(alias, "email").toString()
			admin.password = rs.getString(alias, "password").toString()

			return admin
		}
    }
}
