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
 * Reference model of table transaction_type
 * @author Simpli© CLI generator
 */
@ApiModel
class TransactionType {

    @ApiModelProperty(required = true)
    var idTransactionTypePk: Long = 0

    @ApiModelProperty(required = true)
    var name: String? = null

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (name.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("Name"), Response.Status.NOT_ACCEPTABLE)
		}
		if (name?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("Name", 255), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "transaction_type"): TransactionType {
			val transactionType = TransactionType()

			transactionType.idTransactionTypePk = rs.getLong(alias, "idTransactionTypePk")
			transactionType.name = rs.getString(alias, "name").toString()

			return transactionType
		}
    }
}
