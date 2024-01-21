package net.dixq.test

import android.util.Log
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

class DataClient(serverURI: URI?) : WebSocketClient(serverURI) {

    init {
        connect()
    }
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("dixq","Opened connection")
    }

    override fun onMessage(message: String) {
        Log.d("dixq","Received message: $message")
    }

    override fun onMessage(bytes: ByteBuffer?) {
        // ここで受信したバイトデータを処理
        Log.d("dixq","Received ByteBuffer size : "+bytes!!.capacity())
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("dixq","Closed connection")
    }

    override fun onError(ex: Exception) {
        Log.d("dixq",ex.message.toString())
    }

}
