package com.example.quartoappdio_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    lateinit var btnCalc: FloatingActionButton
    lateinit var listCars: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupListeners()
        setupList()
    }

    fun setupView() {
        btnCalc = findViewById(R.id.fab_goto_calc)
        listCars = findViewById(R.id.rv_list_cars)
    }
    fun setupListeners() {
        btnCalc.setOnClickListener{
            /* forma 1: passando contexto
            val i : Intent = Intent()
            i.setClassName(this, "com.example.quartoappdio_eletriccar.presentation.CalcAutonomyActivity")
            startActivity(i)
            */

            /* forma 2: por package name
            val i : Intent = Intent()
            i.setClassName("com.example.quartoappdio_eletriccar", "com.example.quartoappdio_eletriccar.presentation.CalcAutonomyActivity")
            startActivity(i)
            */

            ///* forma 3: quando activity interna
            startActivity(Intent(this, CalcAutonomyActivity::class.java))
            //*/
        }
    }

    fun setupList() {
        var dados = arrayOf(
            "First",
            "Second",
            "Third"
        )

        val adapter = CarAdapter(dados)
        listCars.layoutManager = LinearLayoutManager(this)
        listCars.adapter = adapter
    }
}
