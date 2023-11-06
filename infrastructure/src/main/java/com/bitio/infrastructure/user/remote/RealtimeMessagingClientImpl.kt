package com.bitio.infrastructure.user.remote


import com.bitio.usercomponent.data.remote.RealtimeMessagingClient
import com.github.nkzawa.socketio.client.IO
import okhttp3.Request

class RealtimeMessagingClientImpl : RealtimeMessagingClient {
    override fun connectSocketIo(chatId: String) {
        val opts = IO.Options()
        opts.query = "chatId=$chatId"
        opts.query =
            "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZmE1YjUzMzdmOTk0NTE2ZGIyZjU5MSIsImlhdCI6MTY5OTE4Mzk1MywiZXhwIjoxNzAxNzc1OTUzfQ.OeGbeRybqvRCGbBcKgNX7zd6hgSWLdz5hRWFTueC70rFud3oFiDDx2oPfYJZR8VM02rFBDey8OHgJAe62z08BGwh9aLX0_xxi2cBVgvvs-Vmefu27Prus8EFFrmK2sobqxpPNle03FEXvERmKQuBnRoepcNpCrYTpq3Cqj5cZ1-yeNsoNQ_6MrA-9kzi9dP8VIzsj02RZ3jhEWUByT_Cfcs24kgVTMQo61TN8Qgk_PreN6TdFXfAZwwF0FdJlYioPHxRT3nylmZpEPngS5FagAjbLxx4IWycocmaR6E59Ov0pGI0p7mHE6WXlrhgqz6BpKMme6DU6hGhU_l21cXCFg"
        val webSocket = IO.socket("http://192.168.1.112:8080", opts)
        webSocket.connect()
            .on("new_message") { parameters ->
                parameters.forEach {
                    println("Sajjadio $it")
                }
            }
    }

    private fun createNewRequest(request: Request, accessToken: String?): Request {
        return request.newBuilder()
            .header(
                "Authorization",
                "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0ZmE1YjUzMzdmOTk0NTE2ZGIyZjU5MSIsImlhdCI6MTY5OTE4Mzk1MywiZXhwIjoxNzAxNzc1OTUzfQ.OeGbeRybqvRCGbBcKgNX7zd6hgSWLdz5hRWFTueC70rFud3oFiDDx2oPfYJZR8VM02rFBDey8OHgJAe62z08BGwh9aLX0_xxi2cBVgvvs-Vmefu27Prus8EFFrmK2sobqxpPNle03FEXvERmKQuBnRoepcNpCrYTpq3Cqj5cZ1-yeNsoNQ_6MrA-9kzi9dP8VIzsj02RZ3jhEWUByT_Cfcs24kgVTMQo61TN8Qgk_PreN6TdFXfAZwwF0FdJlYioPHxRT3nylmZpEPngS5FagAjbLxx4IWycocmaR6E59Ov0pGI0p7mHE6WXlrhgqz6BpKMme6DU6hGhU_l21cXCFg"
            )
            .build()
    }
}