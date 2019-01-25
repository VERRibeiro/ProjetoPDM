package com.example.verri.projetomobile

import java.io.Serializable

class GastoMaximo : Serializable {
    var id : Int
    var preco : Double

    constructor(){
        id = -1
        preco = 0.0
    }
    override fun toString(): String {
        return "${this.preco} $"
    }
}