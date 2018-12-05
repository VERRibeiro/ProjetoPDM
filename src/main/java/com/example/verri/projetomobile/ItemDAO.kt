package com.example.verri.projetomobile

import android.content.ContentValues
import android.content.Context

class ItemDAO {
    private lateinit var banco: BancoHelper
    private val TABLE = "item"

    constructor(context: Context) {
        this.banco = BancoHelper(context)
    }

    // inserir
    fun add(item: Item) {
        val cv = ContentValues()
        cv.put("nome", item.nome)
        cv.put("preco", item.preco)
        cv.put("gastoId", item.gastoId)
        this.banco.writableDatabase.insert(TABLE, null, cv)
    }

    // select
    fun read(): ArrayList<Item> {
        val colunas = arrayOf("id", "nome", "preco", "gastoId")
        val lista = ArrayList<Item>()

        val cursor = this.banco.readableDatabase.query(TABLE, colunas, null, null, null, null, null)

        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                var item = Item()
                item.id = cursor.getInt(cursor.getColumnIndex("id"))
                item.nome = cursor.getString(cursor.getColumnIndex("nome"))
                item.preco = cursor.getDouble(cursor.getColumnIndex("preco"))
                item.gastoId = cursor.getInt(cursor.getColumnIndex("gastoId"))
                lista.add(item)
            } while (cursor.moveToNext())
        }

        return lista
    }

    // select com where
    //Todo
    fun read(id: Int): ArrayList<Item> {
        val colunas = arrayOf("id", "nome", "preco", "gastoId")
        val where = "gastoId = ?"
        val pWhere = arrayOf(id.toString())
        val cursor = this.banco.readableDatabase.query(TABLE, colunas, where, pWhere, null, null, null)
        val lista = ArrayList<Item>()

        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                var item = Item()
                item.id = cursor.getInt(cursor.getColumnIndex("id"))
                item.nome = cursor.getString(cursor.getColumnIndex("nome"))
                item.preco = cursor.getDouble(cursor.getColumnIndex("preco"))
                item.gastoId = cursor.getInt(cursor.getColumnIndex("gastoId"))
                lista.add(item)
            } while (cursor.moveToNext())
        }
        return lista
    }

    // atualização
    fun update(item: Item) {
        val where = "id = ?"
        val pWhere = arrayOf(item.id.toString())
        val cv = ContentValues()
        cv.put("nome", item.nome)
        cv.put("preco", item.preco)
        this.banco.writableDatabase.update(TABLE, cv, where, pWhere)
    }

    // remoção
    fun delete(id: Int) {
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete(TABLE, where, pWhere)
    }
}