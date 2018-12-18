package org.neoneputxoindexer.admin

import org.neoneputxoindexer.RouterWrapper
import br.com.simpli.model.PagedResp
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.HeaderParam
import javax.ws.rs.core.MediaType
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.neoneputxoindexer.admin.process.IndexerProcess

import org.neoneputxoindexer.model.MintHistoryItem

/**
 * Routes of Admin module
 * @author Simpli© CLI generator
 */
@Path("/Admin")
@Api()
@Produces(MediaType.APPLICATION_JSON)
class Router : RouterWrapper() {

    @GET
    @Path("/MintHistory")
    @ApiOperation(value = "List Asset information")
    fun listAsset(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @QueryParam("masterAccount") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<MintHistoryItem> {
        return transacPipe.handle {
            con -> IndexerProcess(con, getLang(lang)).testFunction();
        }
    }

}
