package org.neoneputxoindexer.lang

import br.com.simpli.model.PortugueseLanguage

class PtBr : PortugueseLanguage() {

    init {
        dictionary = hashMapOf(
                "sample_message" to "Amostra de Mensagem"
        )
    }
}
