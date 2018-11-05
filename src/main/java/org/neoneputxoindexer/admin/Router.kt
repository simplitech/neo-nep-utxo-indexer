package org.neoneputxoindexer.admin

import org.neoneputxoindexer.RouterWrapper
import br.com.simpli.model.PagedResp
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.DELETE
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.HeaderParam
import javax.ws.rs.core.MediaType
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam

import org.neoneputxoindexer.admin.process.LoginService
import org.neoneputxoindexer.admin.process.LoginService.LoginSerialized
import org.neoneputxoindexer.admin.response.LoginResp
import org.neoneputxoindexer.admin.process.AdminProcess
import org.neoneputxoindexer.admin.response.AdminResp
import org.neoneputxoindexer.model.Admin
import org.neoneputxoindexer.admin.process.AssetProcess
import org.neoneputxoindexer.admin.response.AssetResp
import org.neoneputxoindexer.model.Asset
import org.neoneputxoindexer.admin.process.BlockProcess
import org.neoneputxoindexer.admin.response.BlockResp
import org.neoneputxoindexer.model.Block
import org.neoneputxoindexer.admin.process.TransactionProcess
import org.neoneputxoindexer.admin.response.TransactionResp
import org.neoneputxoindexer.model.Transaction
import org.neoneputxoindexer.admin.process.TransactionInputProcess
import org.neoneputxoindexer.admin.response.TransactionInputResp
import org.neoneputxoindexer.model.TransactionInput
import org.neoneputxoindexer.admin.process.TransactionTypeProcess
import org.neoneputxoindexer.admin.response.TransactionTypeResp
import org.neoneputxoindexer.model.TransactionType

/**
 * Routes of Admin module
 * @author Simpli© CLI generator
 */
@Path("/Admin")
@Api()
@Produces(MediaType.APPLICATION_JSON)
class Router : RouterWrapper() {

    //    ██████╗  ██████╗ ██╗   ██╗████████╗███████╗██████╗
    //    ██╔══██╗██╔═══██╗██║   ██║╚══██╔══╝██╔════╝██╔══██╗
    //    ██████╔╝██║   ██║██║   ██║   ██║   █████╗  ██████╔╝
    //    ██╔══██╗██║   ██║██║   ██║   ██║   ██╔══╝  ██╔══██╗
    //    ██║  ██║╚██████╔╝╚██████╔╝   ██║   ███████╗██║  ██║
    //    ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝   ╚══════╝╚═╝  ╚═╝

    @GET
    @Path("/Auth")
    @ApiOperation(tags=["LoginResp"], value = "Get user authentication")
    fun auth(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en-US, pt-BR")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): LoginResp {
        //TODO: review generated method
        return transacPipe.handle { con ->
            LoginService(con, getLang(lang), clientVersion)
                .auth(AuthPipe.extractToken(authorization))
        }
    }

    @POST
    @Path("/SignIn")
    @ApiOperation(tags=["LoginResp"], value = "Submit user authentication")
    fun signIn(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en-US, pt-BR")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,

        @ApiParam(required = true)
            body: LoginSerialized
    ): LoginResp {
        //TODO: review generated method
        return transacPipe.handle { con ->
            LoginService(con, getLang(lang), clientVersion)
                .signIn(body.email, body.password)
        }
    }

    @POST
    @Path("/ResetPassword")
    @ApiOperation(tags=["LoginResp"], value = "Reset password of a given account")
    fun resetPassword(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en-US, pt-BR")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,

        body: LoginSerialized
    ): Long {
        return transacPipe.handle { con ->
            LoginService(con, getLang(lang), clientVersion)
                .resetPassword(body.email)
        }
    }

    @POST
    @Path("/RecoverPassword")
    @ApiOperation(tags=["LoginResp"], value = "Recover password of a given hash")
    fun recoverPassword(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en-US, pt-BR")
        lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
        clientVersion: String,

        body: LoginSerialized
    ): String? {
        return transacPipe.handle { con ->
            LoginService(con, getLang(lang), clientVersion)
                .recoverPassword(body.password, body.hash)
        }
    }

    @GET
    @Path("/Admin/{id}")
    @ApiOperation(tags=["Admin"], value = "Gets Admin of informed id")
    fun getAdmin(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): AdminResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AdminProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/Admin")
    @ApiOperation(value = "List Admin information")
    fun listAdmin(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<Admin> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AdminProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/Admin")
    @ApiOperation(tags=["AdminResp"], value = "Persist a new or existing Admin", notes = "1 - Informed Admin have an ID editing the existing Admin; 2 - Informed Admin don't have an ID creating a new Admin")
    fun persistAdmin(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            admin: Admin
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AdminProcess(con, getLang(lang))
                .persist(admin)
        }
    }

    @GET
    @Path("/Asset/{id}")
    @ApiOperation(tags=["Asset"], value = "Gets Asset of informed id")
    fun getAsset(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): AssetResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AssetProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/Asset")
    @ApiOperation(value = "List Asset information")
    fun listAsset(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<Asset> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AssetProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/Asset")
    @ApiOperation(tags=["AssetResp"], value = "Persist a new or existing Asset", notes = "1 - Informed Asset have an ID editing the existing Asset; 2 - Informed Asset don't have an ID creating a new Asset")
    fun persistAsset(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            asset: Asset
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> AssetProcess(con, getLang(lang))
                .persist(asset)
        }
    }

    @GET
    @Path("/Block/{id}")
    @ApiOperation(tags=["Block"], value = "Gets Block of informed id")
    fun getBlock(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): BlockResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> BlockProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/Block")
    @ApiOperation(value = "List Block information")
    fun listBlock(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<Block> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> BlockProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/Block")
    @ApiOperation(tags=["BlockResp"], value = "Persist a new or existing Block", notes = "1 - Informed Block have an ID editing the existing Block; 2 - Informed Block don't have an ID creating a new Block")
    fun persistBlock(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            block: Block
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> BlockProcess(con, getLang(lang))
                .persist(block)
        }
    }

    @GET
    @Path("/Transaction/{id}")
    @ApiOperation(tags=["Transaction"], value = "Gets Transaction of informed id")
    fun getTransaction(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): TransactionResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/Transaction")
    @ApiOperation(value = "List Transaction information")
    fun listTransaction(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<Transaction> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/Transaction")
    @ApiOperation(tags=["TransactionResp"], value = "Persist a new or existing Transaction", notes = "1 - Informed Transaction have an ID editing the existing Transaction; 2 - Informed Transaction don't have an ID creating a new Transaction")
    fun persistTransaction(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            transaction: Transaction
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionProcess(con, getLang(lang))
                .persist(transaction)
        }
    }

    @GET
    @Path("/TransactionInput/{id}")
    @ApiOperation(tags=["TransactionInput"], value = "Gets TransactionInput of informed id")
    fun getTransactionInput(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): TransactionInputResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionInputProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/TransactionInput")
    @ApiOperation(value = "List TransactionInput information")
    fun listTransactionInput(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<TransactionInput> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionInputProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/TransactionInput")
    @ApiOperation(tags=["TransactionInputResp"], value = "Persist a new or existing TransactionInput", notes = "1 - Informed TransactionInput have an ID editing the existing TransactionInput; 2 - Informed TransactionInput don't have an ID creating a new TransactionInput")
    fun persistTransactionInput(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            transactionInput: TransactionInput
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionInputProcess(con, getLang(lang))
                .persist(transactionInput)
        }
    }

    @GET
    @Path("/TransactionType/{id}")
    @ApiOperation(tags=["TransactionType"], value = "Gets TransactionType of informed id")
    fun getTransactionType(
        @PathParam("id") @ApiParam(required = true)
            id: Long?,

        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String
    ): TransactionTypeResp {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionTypeProcess(con, getLang(lang))
                .getOne(id)
        }
    }

    @GET
    @Path("/TransactionType")
    @ApiOperation(value = "List TransactionType information")
    fun listTransactionType(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @QueryParam("query") @ApiParam(value = "Query of search")
            query: String?,
        @QueryParam("page") @ApiParam(value = "Page index, null to not paginate")
            page: Int?,
        @QueryParam("limit") @ApiParam(value = "Page size, null to not paginate")
            limit: Int?,
        @QueryParam("orderBy") @ApiParam(value = "Identifier for sorting, usually a property name", example = "idGrupoDoPrincipalFk")
            orderRequest: String?,
        @QueryParam("ascending") @ApiParam(value = "True for ascending order", defaultValue = "false")
            asc: Boolean?
    ): PagedResp<TransactionType> {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionTypeProcess(con, getLang(lang))
                .list(query, page, limit, orderRequest, asc != null && asc)
        }
    }

    @POST
    @Path("/TransactionType")
    @ApiOperation(tags=["TransactionTypeResp"], value = "Persist a new or existing TransactionType", notes = "1 - Informed TransactionType have an ID editing the existing TransactionType; 2 - Informed TransactionType don't have an ID creating a new TransactionType")
    fun persistTransactionType(
        @HeaderParam("Accept-Language") @ApiParam(required = true, allowableValues = "en, pt")
            lang: String,
        @HeaderParam("X-Client-Version") @ApiParam(required = true, example = "w1.1.0")
            clientVersion: String,
        @HeaderParam("Authorization") @ApiParam(required = true, example = "Bearer mytokenhere")
            authorization: String,

        @ApiParam(required = true)
            transactionType: TransactionType
    ): Long {
        //TODO: review generated method
        return authPipe.handle(authorization, getLang(lang), clientVersion) {
            con, loginInfo -> TransactionTypeProcess(con, getLang(lang))
                .persist(transactionType)
        }
    }

}
