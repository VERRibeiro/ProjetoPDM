package com.example.verri.projetomobile

import android.content.ContentValues
import android.content.Context
import android.util.Log

class GastoMaximoDAO {
    private val TABLE = "gastoMaximo"
    private lateinit var banco: BancoHelper

    constructor(context: Context) {
        this.banco = BancoHelper(context)
    }

    // inserir
    fun add(gastoMaximo: GastoMaximo) {
        val cv = ContentValues()
        cv.put("preco", gastoMaximo.preco)
        this.banco.writableDatabase.insert(TABLE, null, cv)
    }

    // select
    fun read(): ArrayList<GastoMaximo> {
        val colunas = arrayOf("id", "preco")
        val lista = ArrayList<GastoMaximo>()

        val cursor = this.banco.readableDatabase.query(TABLE, colunas, null, null, null, null, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do{
                var gastoMaximo = GastoMaximo()
                gastoMaximo.id = cursor.getInt(cursor.getColumnIndex("id"))
                gastoMaximo.preco= cursor.getDouble(cursor.getColumnIndex("preco"))
                lista.add(gastoMaximo)
            }while(cursor.moveToNext())
        }
        return lista
    }
    fun update(gasto: GastoMaximo){
        val where = "id = ?"
        gasto.id = 0
        val pWhere = arrayOf(gasto.id.toString())
        val cv = ContentValues()
        cv.put("preco",gasto.preco)
        this.banco.writableDatabase.update(TABLE, cv, where, pWhere)
    }
}