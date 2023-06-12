package br.com.theodoro.djikstra_puc_lucia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import br.com.theodoro.djikstra_puc_lucia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var spinnerFrom : Spinner
    private lateinit var spinnerTo : Spinner

    var from = -1;
    var to = -1;

    var adjacencyMatrix = Array(0) { IntArray(0) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerFrom = binding.from
        spinnerTo = binding.to

        ArrayAdapter.createFromResource(
            this,
            R.array.predios_pucc,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerFrom.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.predios_pucc,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerTo.adapter = adapter
        }

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                from = position - 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nenhum item selecionado
            }
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Spinner "to" foi alterado
                println("TOOO")
                to = position - 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nenhum item selecionado
            }
        }
        setContentView(R.layout.activity_main)

        baixarMatrizFirebase()
    }
}