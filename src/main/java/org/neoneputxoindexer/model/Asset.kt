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
 * Reference model of table asset
 * @author Simpli© CLI generator
 */
@ApiModel
class Asset {

    @ApiModelProperty(required = true)
    var idAssetPk: Long = 0

    var name: String? = null

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (name?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("Name", 255), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "asset"): Asset {
			val asset = Asset()

			asset.idAssetPk = rs.getLong(alias, "idAssetPk")
			asset.name = rs.getString(alias, "name")

			return asset
		}
    }
}
