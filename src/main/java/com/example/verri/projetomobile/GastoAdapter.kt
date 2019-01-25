package com.example.verri.projetomobile

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GastoAdapter(var context: Context) : BaseAdapter() {
    private lateinit var dao: GastoDAO
    private lateinit var lista: ArrayList<Gasto>
    init {
        this.dao = GastoDAO(context)
        this.lista = this.dao.read()
    }

    override fun getCount(): Int {
        return this.lista.size
    }

    override fun getItem(position: Int): Any {
        return this.lista[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gasto = this.lista[position]
        val layout: View

        if (convertView == null){
            layout = View.inflate(context, R.layout.gasto_list_layout, null)
        }else{
            layout = convertView
        }

        val tvNome = layout.findViewById<TextView>(R.id.valorTextView)
        val tvData = layout.findViewById<TextView>(R.id.dataTextView)

        tvNome.text = gasto.preco.toString() + " $"
        tvData.text = gasto.tempoStr()

        return layout
    }
}