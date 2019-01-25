package com.example.verri.projetomobile

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_gasto.*

class AddGastoActivity : AppCompatActivity() {
    lateinit var etNomeItemAdd : EditText
    lateinit var etValorItemAdd : EditText
    lateinit var btAdicionarItem : Button
    lateinit var tvItemValor : TextView
    lateinit var itemDao : ItemDAO
    lateinit var lista : ListView
    var gastoId : Int = 0
    var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gasto)
        gastoId = intent.getSerializableExtra("GASTOID").toString().toInt()
        etNomeItemAdd = findViewById(R.id.edNomeItem)
        etValorItemAdd = findViewById(R.id.edValorItem)
        this.tvItemValor = findViewById(R.id.tvItemValor)
        this.itemDao = ItemDAO(this)
        this.btAdicionarItem = findViewById(R.id.btAdicionarItem)
        this.lista = findViewById(R.id.lvProdutos)
        this.lista.adapter = ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, this.itemDao.read(gastoId))
        this.itemDao.read(gastoId).forEach{total += it.preco}
        tvItemValor.text = total.toString()
        this.btAdicionarItem.setOnClickListener({salvar(it)})
        this.btFinalizarGasto.setOnClickListener({finalizar(it)})
        this.lista.setOnItemLongClickListener { parent, view, position, id ->
            val item = this.lista.adapter.getItem(position) as Item
            this.itemDao.delete(item.id)
            atualiza()
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
        val adapter = this.lista.adapter as ArrayAdapter<Item>
        adapter.clear()
        adapter.addAll(this.itemDao.read(gastoId))
        total = 0.0
        this.itemDao.read(gastoId).forEach{total = total +it.preco}
        tvItemValor.setText(total.toString() +" $")
    }
    private fun salvar(view: View){
        val item = Item()
        val nome = this.etNomeItemAdd.text.toString()
        val valor = this.etValorItemAdd.text.toString().toDouble()
        this.etNomeItemAdd.setText("")
        this.etValorItemAdd.setText("")
        item.nome = nome
        item.preco = valor
        item.gastoId = gastoId
        Log.i("GASTOIDDD", item.gastoId.toString())
        itemDao.add(item)
        atualiza()
    }
    private fun finalizar(view: View) {
        val gasto = Gasto()
        gasto.preco = total
        gasto.nome = "Compra" + intent.getSerializableExtra("GASTOID").toString()
        val intent = Intent()
        intent.putExtra("GASTO", gasto)
        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}
