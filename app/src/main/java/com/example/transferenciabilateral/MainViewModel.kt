package com.example.transferenciabilateral

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var saldo: Double = 100.0
        private set

    fun actualizarSaldo(nuevoSaldo: Double) {
        saldo = nuevoSaldo
    }
}