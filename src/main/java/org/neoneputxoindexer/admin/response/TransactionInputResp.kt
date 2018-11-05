package org.neoneputxoindexer.admin.response

import io.swagger.annotations.ApiModelProperty
import org.neoneputxoindexer.model.TransactionInput
import org.neoneputxoindexer.model.Asset
import org.neoneputxoindexer.model.Transaction

/**
 * Response of model TransactionInput
 * @author Simpli© CLI generator
 */
class TransactionInputResp {
    var transactionInput: TransactionInput? = null

    @ApiModelProperty(value = "Possible Asset values")
    var allAsset: MutableList<Asset>? = null

    @ApiModelProperty(value = "Possible Transaction values")
    var allTransaction: MutableList<Transaction>? = null
}
