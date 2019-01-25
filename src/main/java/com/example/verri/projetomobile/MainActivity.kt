package com.example.verri.projetomobile

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var tvValor : TextView
    lateinit var imGasto :ImageView
    lateinit var imMeta : ImageView
    lateinit var btAdicionarLimite : Button
    lateinit var gastoMaximodao : GastoMaximoDAO
    val UPDATE : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.gastoMaximodao = GastoMaximoDAO(this)
        tvValor = findViewById(R.id.tvMainValor)
        imGasto = findViewById(R.id.imGasto)
        imMeta = findViewById(R.id.imMeta)
        if(gastoMaximodao.read().size > 0)
            tvValor.setText(gastoMaximodao.read().get(0).toString())
        else
            tvValor.setText("0.00 $")
        imMeta.setOnClickListener {
            val intent = Intent(this, MetaActivity::class.java)
            startActivityForResult(intent,UPDATE)
        }
        imGasto.setOnClickListener{
            val intent = Intent(this, GastosActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == UPDATE){
                Log.i("QTDE",gastoMaximodao.read().size.toString())
                if(gastoMaximodao.read().size > 0)
                    tvValor.setText("Limite: "+gastoMaximodao.read().get(0).toString())
                else
                    tvValor.setText("0.00 $")
            }
        }
    }
}
