package org.neoneputxoindexer.lang

import br.com.simpli.model.EnglishLanguage

class EnUs : EnglishLanguage() {

    init {
        dictionary = hashMapOf(
                "sample_message" to "Sample Message"
        )
    }
}
