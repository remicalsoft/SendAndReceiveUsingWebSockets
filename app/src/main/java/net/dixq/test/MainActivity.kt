package net.dixq.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.InetSocketAddress
import java.net.URI

class MainActivity : AppCompatActivity() {
    private var _server: DataServer? = null
    private var _client: DataClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            val port = 8080
            _server = DataServer(InetSocketAddress(port))
        }.start()
        Thread {
            _client = DataClient(URI("ws://localhost:8080/echo"))
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _client?.close()
        _server?.stop()
    }
}