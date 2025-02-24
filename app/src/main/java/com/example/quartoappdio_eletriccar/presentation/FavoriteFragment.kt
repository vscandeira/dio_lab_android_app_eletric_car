package com.example.quartoappdio_eletriccar.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.data.CarsApi
import com.example.quartoappdio_eletriccar.domain.Car
import com.example.quartoappdio_eletriccar.domain.local.CarRepository
import com.example.quartoappdio_eletriccar.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteFragment: Fragment() {
    lateinit var favCars: RecyclerView
    lateinit var progressIcon: ProgressBar
    lateinit var noConn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList(getFavCarsLocal())
    }

    override fun onResume() {
        super.onResume()
        setupList(getFavCarsLocal())
    }

    private fun setupView(view: View) {
        view.apply{
            favCars = findViewById(R.id.rv_favorites_cars)
            progressIcon = findViewById(R.id.pb_load_car_screen_fav)
            noConn = findViewById(R.id.iv_empty_fav)
        }
    }

    fun setupList(l : List<Car>) {
        setupPBar(false)
        val adapter = CarAdapter(l, true)
        favCars.adapter = adapter
        adapter.carItemListener = {car: Car ->
            val cr = CarRepository(requireContext())
            cr.save(car)
            //car.isFavorite = cr.getFavorite(car) ?: true
        }
    }

    private fun getFavCarsLocal() : List<Car> {
        val cr = CarRepository(requireContext())
        val favList = cr.getFavCars()
        return favList
    }

    private fun setupPBar(pActivate : Boolean){
        progressIcon.visibility = if(pActivate) View.VISIBLE else View.GONE
        favCars.visibility = if(pActivate) View.GONE else View.VISIBLE
        noConn.visibility = View.GONE
    }

}