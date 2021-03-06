package org.neoneputxoindexer

import org.neoneputxoindexer.lang.EnUs
import org.neoneputxoindexer.lang.PtBr
import br.com.simpli.model.LanguageHolder
import br.com.simpli.model.RespException
import br.com.simpli.sql.TransactionPipe
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

/**
 * Utility class holding properties and methods for all Routers
 * @author Simpli© CLI generator
 */
open class RouterWrapper : ExceptionMapper<Throwable> {
    protected var transacPipe = TransactionPipe("jdbc/neoIndexerDS")

    protected val langs: HashMap<String, LanguageHolder> = object : HashMap<String, LanguageHolder>() {
        init {
            put("en-US", EnUs())
            put("pt-BR", PtBr())
        }
    }

    fun getLang(lang: String = "en-US"): LanguageHolder {
        return langs[lang] ?: EnUs()
    }

    override fun toResponse(e: Throwable): Response {
        Logger.getLogger(RouterWrapper::class.java.name).log(Level.SEVERE, e.message, e)

        if (e is RespException) {
            var code = ""
            if (e.code != null) {
                code = "\"code\": " + e.code + ", "
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{" + code + " \"message\": \"" + e.message + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build()
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Internal Server Error\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build()
        }
    }
}
