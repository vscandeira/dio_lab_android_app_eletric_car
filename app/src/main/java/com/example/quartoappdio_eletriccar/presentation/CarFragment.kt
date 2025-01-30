package com.example.quartoappdio_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.data.CarFactory
import com.example.quartoappdio_eletriccar.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            startActivity(Intent(context, CalcAutonomyActivity::class.java))
        }
    }
}