package com.example.quartoappdio_eletriccar.presentation

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.data.CarFactory
import com.example.quartoappdio_eletriccar.domain.Car
import com.example.quartoappdio_eletriccar.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {
    lateinit var listCars: RecyclerView
    lateinit var btnCalc: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstance: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        setupListeners()
    }

    fun setupView(view: View) {
        view.apply{
            listCars = findViewById(R.id.rv_list_cars)
            btnCalc = findViewById(R.id.fab_goto_calc)
        }
    }

    fun setupList() {
        val adapter = CarAdapter(CarFactory.listCars)
        listCars.adapter = adapter
    }

    fun setupListeners() {
        btnCalc.setOnClickListener {
            MyTask().execute("http://192.168.1.10:8080/api/cars/")
            //startActivity(Intent(context, CalcAutonomyActivity::class.java))
        }
    }

    inner class MyTask : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("DEBUG","My Task - onPreExecute (starting)")
        }
        override fun doInBackground(vararg url: String?): String {
            Log.d("DEBUG","My Task - doInBackground with params: ${url[0]}")

            var urlConnection : HttpURLConnection? = null
            var response : String? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000

                response = urlConnection.inputStream.bufferedReader().use{ it.readText() }
                publishProgress(response)
                urlConnection.disconnect()
            } catch (e : Exception) {
                Log.e("DEBUG", "ERRO NA CONEXÃO\n${e}")
            } finally {
                urlConnection?.disconnect()
            }

            //Log.d("DEBUG", "Response correctly received: \n${response}")
            return response ?: url[0] ?: "No URL Informed"
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            try {
                val jsonArray : JSONArray
                values[0]?.let{
                    jsonArray = JSONTokener(it).nextValue() as JSONArray
                    for (i in 0 until jsonArray.length()){
                        val idProv : Int = jsonArray.getJSONObject(i).getInt("id")
                        val urlPhotoProv : String = jsonArray.getJSONObject(i).getString("urlPhoto")
                        val priceProv : Double?
                        val batteryProv : Double?
                        val powerProv : Double?
                        val chargeProv : Double?
                        var strProv = jsonArray.getJSONObject(i).getString("price")

                        priceProv = if(strProv != "null") strProv.toDouble() else null
                        strProv = jsonArray.getJSONObject(i).getString("battery")
                        batteryProv = if(strProv != "null") strProv.toDouble() else null
                        strProv = jsonArray.getJSONObject(i).getString("power")
                        powerProv = if(strProv != "null") strProv.toDouble() else null
                        strProv = jsonArray.getJSONObject(i).getString("charge")
                        chargeProv = if(strProv != "null") strProv.toDouble() else null

                        val model = Car(
                            id = idProv,
                            price = formatNumber(true, 2, "$ ", priceProv) ?: "-",
                            battery = formatNumber(false, 1, " kWh", batteryProv) ?: "-",
                            power = formatNumber(false, 0, " cv", powerProv) ?: "-",
                            charge = formatNumber(false, 0, " min", chargeProv) ?: "-",
                            urlPhoto = urlPhotoProv
                        )
                        Log.d("DEBUG", "Received JSON Object ${i+1} of price ${model.price}")
                    }
                }
            } catch (e: Exception) {
                Log.e("DEBUG", "ERRO NO PROGRESS UPDATE\n${e}")
            }
        }

        fun formatNumber (inFront : Boolean, numDecimals : Int, formatSTR: String, number : Double?) : String? {
            if(number == null) return null
            val ret = String.format("%,.${numDecimals}f", number)
            if (inFront) {
                return formatSTR + ret
            } else {
                return ret + formatSTR
            }
        }

    }
}