package org.neoneputxoindexer

import br.com.simpli.model.LanguageHolder
import com.amazonaws.regions.Regions
import br.com.simpli.ws.AWSSendEmailRequest

import java.util.HashMap

open class AppMail(appUrl: String, lang: LanguageHolder) : AWSSendEmailRequest(Regions.US_EAST_1) {
    protected var data: MutableMap<String, Any> = HashMap()

    init {
        this.from = "no-reply@neoneputxoindexer.org"
        this.name = "NeoNepUtxoIndexer"

        data["appUrl"] = appUrl
    }
}
