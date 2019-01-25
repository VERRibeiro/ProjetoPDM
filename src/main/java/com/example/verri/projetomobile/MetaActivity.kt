package com.example.verri.projetomobile

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_meta.view.*
import android.support.v4.content.ContextCompat



class MetaActivity : AppCompatActivity() {
    lateinit var btAdicionarLimite : Button
    lateinit var btVoltar : Button
    lateinit var etLimite : EditText
    lateinit var gastoMaximodao : GastoMaximoDAO
    lateinit var gastoDAO : GastoDAO
    lateinit var graficoDeMeta : BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta)
        gastoDAO = GastoDAO(this)
        gastoMaximodao = GastoMaximoDAO(this)
        graficoDeMeta = findViewById(R.id.graficoDeMeta)
        btAdicionarLimite = findViewById(R.id.btAdicionarLimite)
        btVoltar = findViewById(R.id.btVoltarMeta)
        etLimite = findViewById(R.id.etValorLimite)
        btVoltar.setOnClickListener{finish()}
        this.graficoSettings()
        //TODO corrigir bug de update
        btAdicionarLimite.setOnClickListener{
            var gastoMaximo : GastoMaximo = GastoMaximo()
            gastoMaximo.preco = etLimite.text.toString().toDouble()
            if(gastoMaximodao.read().size > 0)
                gastoMaximodao.update(gastoMaximo)
            else
                gastoMaximodao.add(gastoMaximo)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    fun graficoSettings(){
        val barEntries1 : ArrayList<BarEntry> = ArrayList()
        val barEntries2 : ArrayList<BarEntry> = ArrayList()
        this.graficoDeMeta.setDrawBarShadow(false)
        this.graficoDeMeta.setDrawValueAboveBar(true)
        this.graficoDeMeta.setMaxVisibleValueCount(50)
        this.graficoDeMeta.setPinchZoom(false)
        this.graficoDeMeta.setDrawGridBackground(true)
        var gastoFloat : Double
        var limite : Float
        gastoFloat = 0.0
        limite = 0.0f
        if(gastoMaximodao.read().size > 0)
            limite = gastoMaximodao.read().get(0).preco.toFloat()
        gastoDAO.read().forEach{gastoFloat += it.preco}
        barEntries1.add(BarEntry(1f,gastoFloat.toFloat()))
        barEntries2.add(BarEntry(2f,limite))
        val color = ContextCompat.getColor(this, R.color.abc_hint_foreground_material_light)
        val barDataSet1 : BarDataSet = BarDataSet(barEntries1,"Gasto Total")
        barDataSet1.color = color
        val barDataSet2 : BarDataSet = BarDataSet(barEntries2,"Limite")
        val barData : BarData = BarData(barDataSet1, barDataSet2)
        barData.barWidth = 0.9f
        val yAxisLeft : YAxis = graficoDeMeta.axisLeft
        val yAxisRight : YAxis = graficoDeMeta.axisRight
        val valueFormatter : MyAxysValueFormater = MyAxysValueFormater()
        yAxisLeft.isEnabled = false
        yAxisRight.valueFormatter = valueFormatter
        graficoDeMeta.data = barData
    }
}
class MyAxysValueFormater : IAxisValueFormatter{
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return "${value} $"
    }
}
