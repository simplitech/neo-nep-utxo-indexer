package org.neoneputxoindexer.dao

import org.neoneputxoindexer.model.Asset
import com.google.common.base.Strings
import java.sql.Connection
import java.util.ArrayList
import java.util.HashMap
import br.com.simpli.model.LanguageHolder
import br.com.simpli.sql.Dao

/**
 * Responsible for Asset database operations
 * @author Simpli© CLI generator
 */
class AssetDao(con: Connection, lang: LanguageHolder) : Dao(con, lang) {
    
    fun getOne(idAssetPk: Long): Asset? {
        //TODO: review generated method
        return selectOne("""
            SELECT *
            FROM asset
            WHERE 1 = 1
            AND idAssetPk = ?
            """, { rs -> Asset.buildAll(rs) }, idAssetPk)
    }

    fun list(): MutableList<Asset> {
        //TODO: review generated method
        return selectList("""
            SELECT *
            FROM asset
            """, { rs -> Asset.buildAll(rs) })
    }

    fun list(
        query: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): MutableList<Asset> {
        //TODO: review generated method
        val orderRequestAndColumn = HashMap<String, String>()

        orderRequestAndColumn.put("idAssetPk", "asset.idAssetPk")
        orderRequestAndColumn.put("name", "asset.name")

        val orderColumn = orderRequestAndColumn[orderRequest]

        val params = ArrayList<Any>()
        var where = "WHERE 1 = 1 "

        if (!Strings.isNullOrEmpty(query)) {
            where += ("""
                AND LOWER(CONCAT(
                IFNULL(asset.idAssetPk, ''),
                IFNULL(asset.name, '')
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
            FROM asset
            $where
            ${(if (orderColumn != null && asc != null) "ORDER BY " + orderColumn + " " + (if (asc) "ASC " else "DESC ") else "")}
            $limitQuery
            """, { rs -> Asset.buildAll(rs) }, *params.toTypedArray())
    }
    
    fun count(): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idAssetPk)
            FROM asset
            """)
    }

    fun count(search: String?): Int? {
        //TODO: review generated method
        return selectFirstInt("""
            SELECT COUNT(idAssetPk)
            FROM asset
            WHERE LOWER(CONCAT(
            IFNULL(asset.idAssetPk, ''),
            IFNULL(asset.name, '')
            )) LIKE LOWER(?)
            """,
            "%$search%")
    }

    fun updateAsset(asset: Asset): Int {
        //TODO: review generated method
        return update("""
            UPDATE asset
            SET
            name = ?
            WHERE 1 = 1
            AND idAssetPk = ?
            """,
            asset.name,
            asset.idAssetPk
        ).affectedRows
    }

    fun insert(asset: Asset): Long {
        //TODO: review generated method
        return update("""
            INSERT INTO asset (
            name
            ) VALUES (?)
            """,
            asset.name
        ).key
    }

    fun existAsset(idAssetPk: Long): Boolean {
        //TODO: review generated method
        return exist("""
            SELECT idAssetPk
            FROM asset
            WHERE 1 = 1
            AND idAssetPk = ?
            """, idAssetPk)
    }

}
