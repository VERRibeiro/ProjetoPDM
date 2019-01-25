package com.example.verri.projetomobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class TelaDesbloqueadaReceiver: BroadcastReceiver() {
    lateinit var gastoDao : GastoDAO
    override fun onReceive(context: Context, intent: Intent) {
        this.gastoDao = GastoDAO(context)
        var total : Double = 0.0
        this.gastoDao.read().forEach{total += it.preco}
        Toast.makeText(context, "Seu gasto total é " + total + "!", Toast.LENGTH_SHORT).show()
    }
}