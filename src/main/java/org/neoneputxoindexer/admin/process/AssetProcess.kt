package org.neoneputxoindexer.admin.process

import org.neoneputxoindexer.model.Asset
import org.neoneputxoindexer.admin.response.AssetResp
import org.neoneputxoindexer.dao.AssetDao
import org.neoneputxoindexer.exception.HttpException
import com.google.common.base.Strings
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.PagedResp
import java.sql.Connection
import javax.ws.rs.core.Response

/**
 * Asset business logic
 * @author Simpli© CLI generator
 */
class AssetProcess(private val con: Connection, private val lang: LanguageHolder) {

    fun list(
        queryP: String?,
        page: Int?,
        limit: Int?,
        orderRequest: String?,
        asc: Boolean?): PagedResp<Asset> {
        //TODO: review generated method
        var query = queryP
        
        if (query != null) {
            query = query.replace("[.,:\\-\\/]".toRegex(), "")
        }

        val dao = AssetDao(con, lang)

        val listAsset = dao.list(query, page, limit, orderRequest, asc)

        val resp = PagedResp(listAsset)

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

    fun getOne(idAssetPk: Long?): AssetResp {
        //TODO: review generated method
        val assetDao = AssetDao(con, lang)

        val resp = AssetResp()

        if (idAssetPk != null && idAssetPk > 0L) {
            val asset = assetDao.getOne(idAssetPk)
            resp.asset = asset
        }


        return resp
    }

    fun persist(asset: Asset): Long {
        //TODO: review generated method
        val dao = AssetDao(con, lang)

        val idAsset: Long
        if (asset.idAssetPk > 0) {
            asset.validate(true, lang)
            idAsset = asset.idAssetPk
            
            dao.updateAsset(asset)
        } else {
            asset.validate(false, lang)
            idAsset = dao.insert(asset)
            asset.idAssetPk = idAsset
        }

        return idAsset
    }
}
