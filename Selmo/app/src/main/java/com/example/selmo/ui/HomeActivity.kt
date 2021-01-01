package com.example.selmo.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.selmo.R
import com.example.selmo.apiclient.ApiMain
import com.example.selmo.model.Salmo
import com.example.selmo.preferance.SharedPreferance
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    var slist: Salmo? = Salmo()

    val p = ArrayList<Entry>()
    val px = ArrayList<Entry>()
    val pkumulative = ArrayList<Entry>()
    val pkum_x = ArrayList<Entry>()
    val v = ArrayList<Entry>()
    val I = ArrayList<Entry>()
    val danPf = ArrayList<Entry>()
    val sp = SharedPreferance()
    var token: String =""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        actionBar?.hide()

        token = sp.getToken(applicationContext).toString()

        token = SharedPreferance().getToken(applicationContext).toString()
        getData(token)
        btn_logout.setOnClickListener {
            SharedPreferance().clearLoggedInUser(applicationContext)
            onBackPressed()
        }
    }

    private fun getData(token: String) {
        Log.e("TAG", "getData: " + token)
        ApiMain().services.getData(token.toString(), "2").enqueue(object :
            Callback<Salmo> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<Salmo>, response: Response<Salmo>) {
                //Tulis code jika ressponse sukses
                if (response.code() == 200) {
                    slist = response.body()
                    var iadd: Int = 1
                    var valueX: Float = 1434f
                    var before: Float = 0.0f
                    slist!!.sensor.forEach {
                        var i: Float = it.p!!.toFloat()
                        var f: Float = it.v!!.toFloat()
                        var idan = it.i!!.toFloat()
                        var pf = it.pf!!.toFloat()
                        var cek: Float = 0.0.toFloat()
                        val valuePx = i * valueX
                        if (i < cek) {
                            i = "0.0".toFloat()
                        }
                        if (idan < cek) {
                            idan = "0.0".toFloat()
                        }
                        if (f < cek) {
                            f = "0.0".toFloat()
                        }
                        if (pf < cek) {
                            pf = "0.0".toFloat()
                        }

                        if (before != 9999999.0f) {
                            var valeuPkmulatif = before + i
                            var valuePkum_x = valeuPkmulatif * valueX

                            pkumulative.add(Entry(iadd.toFloat(), valeuPkmulatif))
                            pkum_x.add(Entry(iadd.toFloat(), valuePkum_x))
                            Log.e("pkum:", "" + valeuPkmulatif.toString() + " " + before.toString())
                        }
                        p.add(Entry(iadd.toFloat(), i))
                        px.add(Entry(iadd.toFloat(), valuePx))
                        v.add(Entry(iadd.toFloat(), f))
                        I.add(Entry(iadd.toFloat(), idan))
                        danPf.add(Entry(iadd.toFloat(), pf))

                        before = before + i
                        iadd++
                    }


                    val bardataSetp_x = LineDataSet(px, "Selmo Billing (Rp)")
                    lineAll(linePX, bardataSetp_x)
                    var bardataSetpkamulati = LineDataSet(pkumulative, "Selmo P Kumulative (kWh)")
                    lineAll(linepkumulative, bardataSetpkamulati)
                    var bardataSetpkum_x = LineDataSet(pkum_x, "Selmo Kumulative Billing in Rp")
                    lineAll(linepkum_x, bardataSetpkum_x)
                    val bardataSetv = LineDataSet(v, "Selmo V (V)")
                    lineAll(linev, bardataSetv)
//                    LineDataSet(I, "Selmo I (A)")
//                    LineDataSet(danPf, "Selmo PF")

                    val bardataShet = LineDataSet(p, "Selmo P (kWh)")
                    lineAll(lineP, bardataShet)

                    val lineDataSetI = LineDataSet(I, "Selmo I (A)")
                    lineDataSetI.color = Color.GREEN
                    lineDataSetI.valueTextColor = Color.BLACK
                    lineDataSetI.lineWidth = 1f
                    lineDataSetI.valueTextSize = 10f
                    lineDataSetI.setCircleColor(Color.BLUE)
                    lineDataSetI.axisDependency = YAxis.AxisDependency.LEFT
                    val lineDataPF = LineDataSet(danPf, "Selmo PF")
                    lineDataPF.color = Color.BLUE
                    lineDataPF.valueTextColor = Color.BLACK
                    lineDataPF.lineWidth = 1f
                    lineDataPF.valueTextSize = 10f
                    lineDataPF.setCircleColor(Color.RED)
                    lineDataPF.axisDependency = YAxis.AxisDependency.LEFT
                    val dataSets: MutableList<ILineDataSet> = ArrayList()
                    dataSets.add(lineDataSetI)
                    dataSets.add(lineDataPF)
//                    val dataP = LineData(lineDataP)
//                    val dataSet = LineData(lineDataSetI)
//                    val data = CombinedData()
//                    data.setData(dataSet)
//                    data.setData(dataP)
                    val data = LineData(dataSets)
                    lineI_dan_pf.data = data
                    lineI_dan_pf.description.text = "Line Chart Selmo"
                    lineI_dan_pf.animateY(2000)
                } else {
                    Toast.makeText(
                        applicationContext,
                        response.code().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Log.d("asss", "onResponse: " + response.code().toString())
            }

            override fun onFailure(call: Call<Salmo>, t: Throwable) {
                //Tulis code jika response fail
            }
        })
    }
    fun lineAll(viewLine: LineChart, barData: LineDataSet){
        val lineDataSet =LineData(addBarData(barData))
        viewLine.data = lineDataSet

        viewLine.description.text = "Line Chart Selmo"
        viewLine.animateY(2000)
    }
    private fun addBarData(barData: LineDataSet): LineDataSet {

        barData.setGradientColor(Color.BLUE, Color.RED)
        barData.valueTextColor = Color.BLACK
        barData.lineWidth  = 2f
        barData.valueTextSize = 10f
        barData.setCircleColor(Color.GREEN)
        return barData
    }

}