package org.neoneputxoindexer.model

class NotificationBase
{
    var page_len = 0
    var total_pages = 0
    var total = 0
    var page: Int = 0
    var current_height: Long = 0
    val results = ArrayList<Nep5Notification>()
    var message: String = ""
}