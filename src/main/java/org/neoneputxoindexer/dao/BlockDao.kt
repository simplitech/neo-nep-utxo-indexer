package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.Block
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for Block database operations
 * @author Simpli© CLI generator
 */
class BlockDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idBlockPk: Long): Block? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM block
            WHERE 1 = 1
            AND idBlockPk = ?
            """, { rs -> Block.buildAll(rs) }, idBlockPk)
    }

    fun list(): MutableList<Block> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM block
            """, { rs -> Block.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<Block> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idBlockPk", "block.idBlockPk")
        orderRequestAndColumn.put("height", "block.height")
        orderRequestAndColumn.put("hash", "block.hash")
        orderRequestAndColumn.put("creationDate", "block.creationDate")
        orderRequestAndColumn.put("sizeInBytes", "block.sizeInBytes")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(block.idBlockPk, ''),
                IFNULL(block.height, ''),
                IFNULL(block.hash, ''),
                IFNULL(block.creationDate, ''),
                IFNULL(block.sizeInBytes, '')
                )) LIKE LOWER(?)
                """)
            params.add("%$query%")
        }

        var limitQuery = ""
        if (page != null && limit != null) {
            limitQuery = "LIMIT ?, ? "
            params.add(page * limit)
            params.add(limit)
        }

        return selectList("""
            SELECT *
            FROM block
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> Block.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idBlockPk)
            FROM block
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idBlockPk)
            FROM block
            WHERE LOWER(CONCAT(
            IFNULL(block.idBlockPk, ''),
            IFNULL(block.height, ''),
            IFNULL(block.hash, ''),
            IFNULL(block.creationDate, ''),
            IFNULL(block.sizeInBytes, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateBlock(block: Block): Int {
        //TODO: review generated method
        return update("""
            UPDATE block
            SET
            height = ?,
            hash = ?,
            creationDate = ?,
            sizeInBytes = ?
            WHERE 1 = 1
            AND idBlockPk = ?
            """,
            block.height,
            block.hash,
            block.creationDate,
            block.sizeInBytes,
            block.idBlockPk
        ).affectedRows
    }

    fun insert(block: Block): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO block (
            height,
            hash,
            creationDate,
            sizeInBytes
            ) VALUES (?,?,?,?)
            """,
            block.height,
            block.hash,
            block.creationDate,
            block.sizeInBytes
        ).key
    }

    fun existBlock(idBlockPk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idBlockPk
            FROM block
            WHERE 1 = 1
            AND idBlockPk = ?
            """, idBlockPk)
    }

}
