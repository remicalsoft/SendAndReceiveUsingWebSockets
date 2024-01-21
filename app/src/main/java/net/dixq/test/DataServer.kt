package net.dixq.test

import android.util.Log
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

class DataServer(address: InetSocketAddress?) : WebSocketServer(address) {

    init {
        start()
        Log.d("dixq", "DataServer started on port: $port")
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake?) {
        Log.d("dixq", "New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress())
        // ここで大量データの送信を開始できます
        sendLargeData(conn, ByteArray(1024*1024*100))
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Log.d("dixq", "Closed connection")
    }

    override fun onMessage(conn: WebSocket?, message: String) {
        Log.d("dixq", "Received message: $message")
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        Log.d("dixq", "onError : "+ex.message.toString());
    }

    override fun onStart() {
        Log.d("dixq", "onStart");
    }

    fun sendLargeData(conn: WebSocket, largeData: ByteArray) {
        val CHUNK_SIZE = 1024 * 1024 // 1MBのチャンクサイズ
        val totalSize = largeData.size
        var sentSize = 0
        while (sentSize < totalSize) {
            val remainingSize = totalSize - sentSize
            val chunkSize = Math.min(remainingSize, CHUNK_SIZE)
            val chunk = ByteBuffer.wrap(largeData, sentSize, chunkSize)
            conn.send(chunk)
            sentSize += chunkSize
        }
    }
}