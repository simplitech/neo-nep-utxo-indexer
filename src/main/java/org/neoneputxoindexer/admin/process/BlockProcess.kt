package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.Block
import org.neoneputxoindexer.admin.response.BlockResp
import org.neoneputxoindexer.dao.BlockDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * Block business logic
 * @author Simpli© CLI generator
 */
class BlockProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<Block> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = BlockDao(con, lang)

        val listBlock = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listBlock)

        if (!Strings.isNullOrEmpty(query)) {
            dao.count(query)?.let {
                count -> resp.recordsTotal = count
            }
        } else {
            dao.count()?.let{
                count -> resp.recordsTotal = count
            }
        }

        return resp
    }

    fun getOne(idBlockPk: Long?): BlockResp {
        //TODO: review generated method
        val blockDao = BlockDao(con, lang)

        val resp = BlockResp()

        if (idBlockPk != null && idBlockPk > 0L) {
            val block = blockDao.getOne(idBlockPk)
            resp.block = block
        }


        return resp
    }

    fun persist(block: Block): Long {
        //TODO: review generated method
        val dao = BlockDao(con, lang)

        val idBlock: Long
        if (block.idBlockPk > 0) {
            block.validate(true, lang)
            idBlock = block.idBlockPk
            
            dao.updateBlock(block)
        } else {
            block.validate(false, lang)
            idBlock = dao.insert(block)
            block.idBlockPk = idBlock
        }

        return idBlock
    }
}
