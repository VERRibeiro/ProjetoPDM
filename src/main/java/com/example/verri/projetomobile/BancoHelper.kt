package com.example.verri.projetomobile

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoHelper(context: Context):
        SQLiteOpenHelper(context, "amigos.db", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val sql1 = "create table if not exists gasto(" +
                "id integer primary key autoincrement," +
                " nome text," +
                " preco real," +
                " tempo timestamp)"
        val sql2 = "create table if not exists item(" +
                "id integer primary key autoincrement," +
                " nome text," +
                " preco real," +
                " gastoId integer)"
        db?.execSQL(sql1)
        db?.execSQL(sql2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}