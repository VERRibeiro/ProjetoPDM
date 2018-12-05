package com.example.verri.projetomobile

import android.content.ContentValues
import android.content.Context

class GastoDAO{
    private lateinit var banco: BancoHelper
    private val TABLE = "gasto"

    constructor(context: Context){
        this.banco = BancoHelper(context)
    }

    // inserir
    fun add(gasto: Gasto){
        val cv = ContentValues()
        cv.put("nome", gasto.nome)
        cv.put("preco", gasto.preco)
        cv.put("tempo", gasto.tempo.timeInMillis)
        this.banco.writableDatabase.insert(TABLE, null, cv)
    }

    // select
    fun read(): ArrayList<Gasto>{
        val colunas = arrayOf("id", "nome","preco", "tempo")
        val lista = ArrayList<Gasto>()

        val cursor = this.banco.readableDatabase.query(TABLE, colunas, null, null, null, null, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do{
                var gasto = Gasto()
                gasto.id = cursor.getInt(cursor.getColumnIndex("id"))
                gasto.nome = cursor.getString(cursor.getColumnIndex("nome"))
                gasto.preco = cursor.getDouble(cursor.getColumnIndex("preco"))
                gasto.tempo.timeInMillis = cursor.getLong(cursor.getColumnIndex("tempo"))
                lista.add(gasto)
            }while(cursor.moveToNext())
        }

        return lista
    }

    // select com where
    fun read(id: Int): Gasto?{
        val colunas = arrayOf("id", "nome", "preco", "tempo")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        val cursor = this.banco.readableDatabase.query(TABLE, colunas, where, pWhere, null, null, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            var gasto = Gasto()
            gasto.id = cursor.getInt(cursor.getColumnIndex("id"))
            gasto.nome = cursor.getString(cursor.getColumnIndex("nome"))
            gasto.preco = cursor.getDouble(cursor.getColumnIndex("gasto"))
            gasto.tempo.timeInMillis = cursor.getLong(cursor.getColumnIndex("tempo"))
            return gasto
        }

        return null
    }

    // atualização
    fun update(gasto: Gasto){
        val where = "id = ?"
        val pWhere = arrayOf(gasto.id.toString())
        val cv = ContentValues()
        cv.put("nome", gasto.nome)
        cv.put("preco",gasto.preco)
        this.banco.writableDatabase.update(TABLE, cv, where, pWhere)
    }
    // select
    fun size(): Int{
        val colunas = arrayOf("id", "nome","preco", "tempo")
        val lista = ArrayList<Gasto>()

        val cursor = this.banco.readableDatabase.query(TABLE, colunas, null, null, null, null, null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do{
                var gasto = Gasto()
                gasto.id = cursor.getInt(cursor.getColumnIndex("id"))
                gasto.nome = cursor.getString(cursor.getColumnIndex("nome"))
                gasto.preco = cursor.getDouble(cursor.getColumnIndex("preco"))
                gasto.tempo.timeInMillis = cursor.getLong(cursor.getColumnIndex("tempo"))
                lista.add(gasto)
            }while(cursor.moveToNext())
        }
        if(lista!= null)
            return lista.size
        return 0
    }
    // remoção
    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete(TABLE, where, pWhere)
    }
}