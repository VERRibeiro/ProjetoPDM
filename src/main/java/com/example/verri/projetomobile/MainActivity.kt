package com.example.verri.projetomobile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var tvValor : TextView
    lateinit var btGasto : Button
    lateinit var btDesejo : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvValor = findViewById(R.id.tvMainValor)
        btGasto = findViewById(R.id.btMainGasto)
        btDesejo = findViewById(R.id.btMainDesejo)

        btGasto.setOnClickListener({
            val intent = Intent(this, GastosActivity::class.java)
            startActivity(intent)
        })
    }


}
