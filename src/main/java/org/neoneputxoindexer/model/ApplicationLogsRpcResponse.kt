package org.neoneputxoindexer.model

class ApplicationLogsRpcResponse : RpcResponse()
{
    val result  = NotificationBase()
}

class NotificationBase
{
    val txid = ""
    val notifications = ArrayList<Notification>()
}

class Notification
{
    val contract = ""
    val state = NotificationState()
}

class NotificationState
{
    val type = ""
    val value = ArrayList<NotificationStateValue>()
}

class NotificationStateValue
{
    val type = ""
    val value = ""
}