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
        btVoltar = findViewById(R.id.btVoltar)
        this.tvValorGasto = findViewById(R.id.tvValorGasto)
        this.gastoDao = GastoDAO(this)
        this.itemDao = ItemDAO(this)
        this.lista = findViewById(R.id.lvGastos)
        this.lista.adapter = ArrayAdapter<Gasto>(this, android.R.layout.simple_list_item_1, this.gastoDao.read())
        this.gastoDao.read().forEach{total += it.preco}
        tvValorGasto.setText(total.toString())
        btCadastrarGastos.setOnClickListener {
            intent = Intent(this, AddGastoActivity::class.java)
            intent.putExtra("GASTOID", gastoDao.size())
            startActivityForResult(intent, ADD)
        }
        this.lista.setOnItemLongClickListener { parent, view, position, id ->
            val gasto = this.lista.adapter.getItem(position) as Gasto
            this.gastoDao.delete(gasto.id)
            total -= gasto.preco
            this.itemDao.read(gasto.id).forEach{ Log.i("DALE", it.id.toString())}
            tvValorGasto.setText(total.toString())
            atualiza()
            true
        }
        this.lista.setOnItemClickListener { parent, view, position, id ->
            val gasto = this.lista.adapter.getItem(position) as Gasto
            intent = Intent(this, AddGastoActivity::class.java)
            intent.putExtra("GASTOID", gasto.id)
            startActivityForResult(intent, EDIT)
            true
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
        tvValorGasto.text = total.toString() + " $"
        val adapter = this.lista.adapter as ArrayAdapter<Gasto>
        adapter.clear()
        adapter.addAll(this.gastoDao.read())
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
