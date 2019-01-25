package com.example.verri.projetomobile

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class GastosActivity : AppCompatActivity() {
    lateinit var btCadastrarGastos: Button
    lateinit var lista : ListView
    lateinit var btVoltar : Button
    lateinit var gastoDao : GastoDAO
    lateinit var itemDao : ItemDAO
    lateinit var tvValorGasto: TextView
    var total : Double = 0.0
    val ADD = 1
    val EDIT = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        btCadastrarGastos = findViewById(R.id.btCadastrarGasto)
        btVoltar = findViewById(R.id.btVoltarGasto)
        this.tvValorGasto = findViewById(R.id.tvValorGasto)
        this.gastoDao = GastoDAO(this)
        this.itemDao = ItemDAO(this)
        this.lista = findViewById(R.id.lvGastos)
        this.lista.adapter = GastoAdapter(this)

        if (this.gastoDao.read().size > 0){
            this.gastoDao.read().forEach{total += it.preco}
        }
        tvValorGasto.setText("Gastos: "+total.toString() + " $")
        btCadastrarGastos.setOnClickListener {
            intent = Intent(this, AddGastoActivity::class.java)
            intent.putExtra("GASTOID", gastoDao.size())
            startActivityForResult(intent, ADD)
        }
        this.lista.setOnItemClickListener { parent, view, position, id ->
            val gasto = this.lista.adapter.getItem(position) as Gasto
            val intent = Intent(this, AddGastoActivity::class.java)
            intent.putExtra("GASTOID", gasto.id - 1)
            startActivityForResult(intent, EDIT)
            true
        }
        btVoltar.setOnClickListener{
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun atualiza(){
        //(this.lista.adapter as ArrayAdapter<Amigo>).notifyDataSetChanged()
        total = 0.0
        this.gastoDao.read().forEach{total += it.preco}
        tvValorGasto.text = "Gastos: "+total.toString() + " $"
        this.lista.adapter = GastoAdapter(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            val gasto = data?.getSerializableExtra("GASTO") as Gasto
            //Log.i("AMIGO", amigo.toString())

            if (requestCode == ADD){
                this.gastoDao.add(gasto)
                //Log.i("AMIGO", this.dao.read().toString())
            }else{
                this.gastoDao.update(gasto)
            }
            this.atualiza()
        }
    }
}
