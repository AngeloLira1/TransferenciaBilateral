package com.example.transferenciabilateral

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.transferenciabilateral.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el saldo enviado desde MainActivity
        val saldoRecibido = intent.getDoubleExtra("EXTRA_SALDO_INICIAL", 0.0)
        binding.tvSaldoRecibido.text = "Saldo recibido: $saldoRecibido"

        binding.btnConfirmarRetiro.setOnClickListener {
            val retiro = binding.etMontoRetiro.text.toString().toDoubleOrNull()

            if (retiro != null) {
                if (retiro <= saldoRecibido) {
                    val nuevoSaldo = saldoRecibido - retiro

                    // Construir el Intent de retorno
                    val resultIntent = Intent().apply {
                        putExtra("EXTRA_NUEVO_SALDO", nuevoSaldo)
                    }

                    // Establecer el resultado y finalizar la Activity
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } else {
                    binding.etMontoRetiro.error = "El retiro supera el saldo disponible"
                }
            } else {
                binding.etMontoRetiro.error = "Ingresa un monto válido"
            }
        }
    }
}