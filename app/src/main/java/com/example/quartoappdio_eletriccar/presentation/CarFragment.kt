package com.example.quartoappdio_eletriccar.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.General
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.data.CarsApi
import com.example.quartoappdio_eletriccar.domain.Car
import com.example.quartoappdio_eletriccar.domain.local.CarRepository
import com.example.quartoappdio_eletriccar.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {
    lateinit var listCars: RecyclerView
    lateinit var btnCalc: FloatingActionButton
    lateinit var progressIcon: ProgressBar
    lateinit var noConn: ImageView
    lateinit var carsApi : CarsApi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstance: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListeners()
        val connected = checkForInternet(context)
        if (connected) {
            callApiRetrofit()
        } else {
            setupNoConnect()
        }
    }

    override fun onResume() {
        super.onResume()
        val connected = checkForInternet(context)
        if (connected) {
            callApiRetrofit()
        } else {
            setupNoConnect()
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.10:9999/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    private fun setupView(view: View) {
        view.apply{
            listCars = findViewById(R.id.rv_list_cars)
            btnCalc = findViewById(R.id.fab_goto_calc)
            progressIcon = findViewById(R.id.pb_load_car_screen)
            noConn = findViewById(R.id.iv_empty)
        }
    }

    fun setupList(l : List<Car>) {
        setupPBar(false)
        val adapter = CarAdapter(l)
        listCars.adapter = adapter
        adapter.carItemListener = {car: Car ->
            val cr = CarRepository(requireContext())
            cr.save(car)
            //car.isFavorite = cr.getFavorite(car) ?: true
        }
    }

    private fun setupListeners() {
        btnCalc.setOnClickListener {
            startActivity(Intent(context, CalcAutonomyActivity::class.java))
        }
    }

    private fun callApiRetrofit() {
        setupPBar(true)
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful){
                    response.body()?.let{
                        setupList(General.treatCarsStrings(it, requireContext()))
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun setupPBar(pActivate : Boolean){
        progressIcon.visibility = if(pActivate) View.VISIBLE else View.GONE
        listCars.visibility = if(pActivate) View.GONE else View.VISIBLE
        noConn.visibility = View.GONE
    }

    private fun setupNoConnect() {
        progressIcon.visibility = View.GONE
        listCars.visibility = View.GONE
        noConn.visibility = View.VISIBLE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkForInternet(context: Context?) : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }

    }

}