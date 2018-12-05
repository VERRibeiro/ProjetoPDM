package com.example.verri.projetomobile

import java.io.Serializable
import java.util.*

class Item: Serializable {
    var id: Int
    var gastoId: Int
    var nome: String
    var preco : Double
    var tempo: Calendar

    constructor(){
        this.id = -1
        this.nome = ""
        this.preco = 0.0
        this.tempo = Calendar.getInstance()
        this.gastoId = -1
    }
    fun tempoStr(): String{
        val dia = this.tempo.get(Calendar.DAY_OF_MONTH)
        val mes = this.tempo.get(Calendar.MONTH) + 1
        val ano = this.tempo.get(Calendar.YEAR)
        return "${dia}/${mes}/${ano}"
    }

    override fun toString(): String {
        return "${this.gastoId}: ${this.nome} - ${this.preco} (${this.tempoStr()})"
    }
}