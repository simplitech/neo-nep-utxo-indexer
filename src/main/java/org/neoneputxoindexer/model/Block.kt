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
 * Reference model of table block
 * @author Simpli© CLI generator
 */
@ApiModel
class Block {

    @ApiModelProperty(required = true)
    var idBlockPk: Long = 0

    @ApiModelProperty(required = true)
    var height: Long = 0

    @ApiModelProperty(required = true)
    var hash: Long = 0

    @ApiModelProperty(required = true)
    var creationDate: Long = 0

    @ApiModelProperty(required = true)
    var sizeInBytes: Long = 0

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (height == 0L) {
			throw HttpException(lang.cannotBeNull("Height"), Response.Status.NOT_ACCEPTABLE)
		}
		if (hash == 0L) {
			throw HttpException(lang.cannotBeNull("Hash"), Response.Status.NOT_ACCEPTABLE)
		}
		if (creationDate == 0L) {
			throw HttpException(lang.cannotBeNull("Creation Date"), Response.Status.NOT_ACCEPTABLE)
		}
		if (sizeInBytes == 0L) {
			throw HttpException(lang.cannotBeNull("Size In Bytes"), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "block"): Block {
			val block = Block()

			block.idBlockPk = rs.getLong(alias, "idBlockPk")
			block.height = rs.getLong(alias, "height")
			block.hash = rs.getLong(alias, "hash")
			block.creationDate = rs.getLong(alias, "creationDate")
			block.sizeInBytes = rs.getLong(alias, "sizeInBytes")

			return block
		}
    }
}
