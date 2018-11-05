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
 * Reference model of table transaction_input
 * @author Simpli© CLI generator
 */
@ApiModel
class TransactionInput {
    var asset: Asset? = null
    var transaction1: Transaction? = null
    var transaction2: Transaction? = null

    @ApiModelProperty(required = true)
    var idTransactionInputPk: Long = 0

    var previousIdTransactionFk: Long?
        get() = if (transaction2 == null || transaction2?.idTransactionPk == 0L) null else transaction2?.idTransactionPk
        set(previousIdTransactionFk) {
            if (previousIdTransactionFk == null) {
                transaction2 = null
                return
            }
            if (transaction2 == null) {
                transaction2 = Transaction()
            }
            transaction2?.idTransactionPk = previousIdTransactionFk
        }

    var idTransactionFk: Long
        get() = transaction1?.idTransactionPk ?: 0
        set(idTransactionFk) {
            if (transaction1 == null) {
                transaction1 = Transaction()
            }
            transaction1?.idTransactionPk = idTransactionFk
        }

    @ApiModelProperty(required = true)
    var amount: Long = 0

    @ApiModelProperty(required = true)
    var spent: Boolean = false

    @ApiModelProperty(required = true)
    var to: String? = null

    var idAssetFk: Long?
        get() = if (asset == null || asset?.idAssetPk == 0L) null else asset?.idAssetPk
        set(idAssetFk) {
            if (idAssetFk == null) {
                asset = null
                return
            }
            if (asset == null) {
                asset = Asset()
            }
            asset?.idAssetPk = idAssetFk
        }

    constructor() {}

    fun validate(updating: Boolean, lang: LanguageHolder) {
        if (updating) {
            //TODO: Specify updating validation
        }

		if (idTransactionFk == 0L) {
			throw HttpException(lang.cannotBeNull("Id Transaction Fk"), Response.Status.NOT_ACCEPTABLE)
		}
		if (amount == 0L) {
			throw HttpException(lang.cannotBeNull("Amount"), Response.Status.NOT_ACCEPTABLE)
		}
		if (to.isNullOrEmpty()) {
			throw HttpException(lang.cannotBeNull("To"), Response.Status.NOT_ACCEPTABLE)
		}
		if (to?.length ?: 0 > 255) {
			throw HttpException(lang.lengthCannotBeMoreThan("To", 255), Response.Status.NOT_ACCEPTABLE)
		}
    }

    companion object {
		@Throws(SQLException::class)
		@JvmOverloads
		fun buildAll(rs: ResultSet, alias: String = "transaction_input"): TransactionInput {
			val transactionInput = TransactionInput()

			transactionInput.idTransactionInputPk = rs.getLong(alias, "idTransactionInputPk")
			transactionInput.previousIdTransactionFk = rs.getLongOrNull(alias, "previousIdTransactionFk")
			transactionInput.idTransactionFk = rs.getLong(alias, "idTransactionFk")
			transactionInput.amount = rs.getLong(alias, "amount")
			transactionInput.spent = rs.getBoolean(alias, "spent")
			transactionInput.to = rs.getString(alias, "to").toString()
			transactionInput.idAssetFk = rs.getLongOrNull(alias, "idAssetFk")

			return transactionInput
		}
    }
}
