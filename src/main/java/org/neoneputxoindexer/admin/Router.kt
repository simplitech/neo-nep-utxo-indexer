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

import org.neoneputxoindexer.model.TransactionStats
import org.neoneputxoindexer.model.TransferTransaction
import java.util.*

/**
 * Routes of Admin module
 * @author Simpli© CLI generator
 */
@Path("/Indexer")
@Api()
@Produces(MediaType.APPLICATION_JSON)
class Router : RouterWrapper() {

    @GET
    @Path("/Transaction")
    @ApiOperation(value = "List Transfer Information")
    fun listTransfers(
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
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "transactionHash")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
        asc: Boolean?,
        @QueryParam("startDate") @ApiParam(value = "Starting from", example = "2018-10-19")
        startDate: Date?,
        @QueryParam("endDate") @ApiParam(value = "Ending in", example = "2018-10-25")
        endDate: Date?
    ): PagedResp<TransferTransaction> {
        return transacPipe.handle {
            con -> IndexerProcess(con, getLang(lang)).listTransactions(query, page, limit, orderRequest, startDate, endDate, asc);
        }
    }

    @GET
    @Path("/Transaction/Stats")
    @ApiOperation(value = "Get Transaction Statistics")
    fun countTransactions(
            @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
            @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
            @QueryParam("startDate") @ApiParam(value = "Starting from", defaultValue = "2018-10-19")
            startDate: Date?,
            @QueryParam("endDate") @ApiParam(value = "Ending in", defaultValue = "2018-10-25")
            endDate: Date?
    ): List<TransactionStats> {
        return transacPipe.handle {
            con -> IndexerProcess(con, getLang(lang)).getTransactionsStats(startDate, endDate);
        }
    }

    @GET
    @Path("/SchedulerRun")
    @ApiOperation(value = "Runs the scheduler")
    fun runIndexer(): Boolean {
        return transacPipe.handle {
            con -> IndexerProcess(con, getLang()).schedulerRun()
        }
    }

}
