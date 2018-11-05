package org.neoneputxoindexer.admin.response

import io.swagger.annotations.ApiModelProperty
import org.neoneputxoindexer.model.Transaction
import org.neoneputxoindexer.model.Block
import org.neoneputxoindexer.model.TransactionType

/**
 * Response of model Transaction
 * @author Simpli© CLI generator
 */
class TransactionResp {
    var transaction: Transaction? = null

    @ApiModelProperty(value = "Possible Block values")
    var allBlock: MutableList<Block>? = null

    @ApiModelProperty(value = "Possible TransactionType values")
    var allTransactionType: MutableList<TransactionType>? = null
}
