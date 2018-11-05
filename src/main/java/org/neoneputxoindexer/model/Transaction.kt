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
 * Reference model of table transaction
 * @author Simpli© CLI generator
 */
@ApiModel
class Transaction {
    var block: Block? = null
    var transactionType: TransactionType? = null

    @ApiModelProperty(required = true)
    var idTransactionPk: Long = 0

    @ApiModelProperty(required = true)
    var hash: String? = null

    var idTypeFk: Long
        get() = transactionType?.idTransactionTypePk ?: 0
        set(idTypeFk) {
            if (transactionType == null) {
                transactionType = TransactionType()
            }
            transactionType?.idTransactionTypePk = idTypeFk
        }

    @ApiModelProperty(required = true)
    var from: String? = null

    var idBlockFk: Long
        get() = block?.idBlockPk ?: 0
        set(idBlockFk) {
            if (block == null) {
                block = Block()
            }
            block?.idBlockPk = idBlockFk
        }

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (hash.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("Hash"), Response.Status.NOT_ACCEPTABLE)
		}
		if (hash?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("Hash", 255), Response.Status.NOT_ACCEPTABLE)
		}
		if (idTypeFk == 0L) {
			throw HttpException(lang.cannotBeNull("Id Type Fk"), Response.Status.NOT_ACCEPTABLE)
		}
		if (from.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("From"), Response.Status.NOT_ACCEPTABLE)
		}
		if (from?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("From", 255), Response.Status.NOT_ACCEPTABLE)
		}
		if (idBlockFk == 0L) {
			throw HttpException(lang.cannotBeNull("Id Block Fk"), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "transaction"): Transaction {
			val transaction = Transaction()

			transaction.idTransactionPk = rs.getLong(alias, "idTransactionPk")
			transaction.hash = rs.getString(alias, "hash").toString()
			transaction.idTypeFk = rs.getLong(alias, "idTypeFk")
			transaction.from = rs.getString(alias, "from").toString()
			transaction.idBlockFk = rs.getLong(alias, "idBlockFk")

			return transaction
		}
    }
}
