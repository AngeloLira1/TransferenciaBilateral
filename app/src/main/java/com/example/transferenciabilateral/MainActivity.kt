package com.example.transferenciabilateral

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.transferenciabilateral.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    // Registrar el launcher para recibir la respuesta de DetailActivity
    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val nuevoSaldo = result.data?.getDoubleExtra("EXTRA_NUEVO_SALDO", -1.0) ?: -1.0
            if (nuevoSaldo != -1.0) {
                // Actualizar ViewModel y pantalla
                viewModel.actualizarSaldo(nuevoSaldo)
                binding.tvSaldoActual.text = "Saldo actual: $nuevoSaldo"
                Toast.makeText(this, "Saldo actualizado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar saldo guardado en el ViewModel al iniciar
        binding.tvSaldoActual.text = "Saldo actual: ${viewModel.saldo}"

        binding.btnEnviarSaldo.setOnClickListener {
            val saldoInput = binding.etSaldoInicial.text.toString().toDoubleOrNull()

            if (saldoInput != null) {
                viewModel.actualizarSaldo(saldoInput)

                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("EXTRA_SALDO_INICIAL", saldoInput)
                }

                // Enviar Intent esperando un resultado
                detailLauncher.launch(intent)
            } else {
                binding.etSaldoInicial.error = "Ingresa un saldo válido"
            }
        }
    }
}