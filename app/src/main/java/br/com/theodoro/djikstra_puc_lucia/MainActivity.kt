package br.com.theodoro.djikstra_puc_lucia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class MainActivity : AppCompatActivity() {
    var adjacencyMatrix = Array(0) { IntArray(0) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baixarMatrizFirebase()
    }
}