package com.example.quartoappdio_eletriccar.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import com.example.quartoappdio_eletriccar.R

class CalcAutonomyActivity : ComponentActivity() {
    lateinit var price: EditText
    lateinit var trav: EditText
    lateinit var res_text: TextView
    lateinit var res_val: TextView
    lateinit var btnCalc: Button
    lateinit var btnGoBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc_autonomy)
        setupView()
        setupListeners()
    }

    fun setupView() {
        btnCalc = findViewById(R.id.bt_calc)
        btnGoBack = findViewById(R.id.iv_left_arrow)
        price = findViewById(R.id.et_price_km)
        trav = findViewById(R.id.et_traveled)
        res_text = findViewById(R.id.tv_result)
        res_val = findViewById(R.id.tv_result_val)
    }
    fun setupListeners() {
        btnCalc.setOnClickListener{
            val result = calculate()
            res_text.visibility = View.VISIBLE
            res_val.text = result.toString()
            res_val.visibility = View.VISIBLE

        }

        btnGoBack.setOnClickListener{
            //primeira forma empilha mais uma activity e consome mais memória
            //startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun calculate(): Float {
        val inpPrice = price.text.toString().toFloat()
        val inpTrav = trav.text.toString().toFloat()
        return inpPrice/inpTrav
    }

}