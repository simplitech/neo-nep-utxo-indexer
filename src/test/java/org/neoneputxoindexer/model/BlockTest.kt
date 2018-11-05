package org.neoneputxoindexer.model

import org.neoneputxoindexer.exception.HttpException
import br.com.simpli.model.EnglishLanguage
import br.com.simpli.model.RespException
import java.util.ArrayList
import java.util.Date
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests Block
 * @author Simpli© CLI generator
 */
class BlockTest {

    @Test(expected = HttpException::class)
    fun testValidateInHeight() {
        val block = Block()
        block.hash = 1
        block.creationDate = 1
        block.sizeInBytes = 1

        block.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInHash() {
        val block = Block()
        block.height = 1
        block.creationDate = 1
        block.sizeInBytes = 1

        block.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInCreationDate() {
        val block = Block()
        block.height = 1
        block.hash = 1
        block.sizeInBytes = 1

        block.validate(false, EnglishLanguage())
    }

    @Test(expected = HttpException::class)
    fun testValidateInSizeInBytes() {
        val block = Block()
        block.height = 1
        block.hash = 1
        block.creationDate = 1

        block.validate(false, EnglishLanguage())
    }

    @Test
    fun testValidateSuccess() {
        val block = Block()
        block.idBlockPk = 1
        block.height = 1
        block.hash = 1
        block.creationDate = 1
        block.sizeInBytes = 1

        block.validate(false, EnglishLanguage())
    }
}
