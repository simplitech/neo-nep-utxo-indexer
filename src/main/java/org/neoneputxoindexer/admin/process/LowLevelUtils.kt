package org.neoneputxoindexer.admin.process

import org.apache.commons.codec.binary.Hex
import java.math.BigInteger

object LowLevelUtils {
    fun reverseHex(input: String): String {
        val result = StringBuilder()
        var i = 0
        while (i <= input.length - 2) {
            result.append(StringBuilder(input.substring(i, i + 2)).reverse())
            i += 2
        }
        return result.reverse().toString()
    }

    fun hex2str(input: String): String {
        val notificationNameBytes = Hex.decodeHex(input.toCharArray())
        return String(notificationNameBytes)
    }

    fun hex2Int(input: String): Int {
        var reversed = LowLevelUtils.reverseHex(input)
        val amountBytes = Hex.decodeHex(reversed.toCharArray())
        return BigInteger(amountBytes).intValueExact()
    }
}