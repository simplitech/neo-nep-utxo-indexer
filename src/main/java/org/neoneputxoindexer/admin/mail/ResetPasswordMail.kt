package org.neoneputxoindexer.admin.mail

import org.neoneputxoindexer.AppMail
import org.neoneputxoindexer.model.Admin
import br.com.simpli.model.LanguageHolder

class ResetPasswordMail(appUrl: String, lang: LanguageHolder, admin: Admin, hash: String) : AppMail(appUrl, lang) {
    init {
        this.to = admin.email!!
        this.subject = "Password Recovery"

        data.put("linkUrl", """$appUrl/#/password/recover/$hash""")
        data.put("linkUnsubscribeUrl", "#")

        setBodyFromTemplate(this::class.java, data, "forgotPassword.html")
    }
}
