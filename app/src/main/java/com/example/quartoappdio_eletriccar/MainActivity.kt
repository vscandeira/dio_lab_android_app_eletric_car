package com.example.quartoappdio_eletriccar

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import com.example.quartoappdio_eletriccar.ui.theme.QuartoAppDio_EletricCarTheme

class MainActivity : ComponentActivity() {
    lateinit var price: EditText
    lateinit var trav: EditText
    lateinit var btnCalc: Button
    lateinit var res_text: TextView
    lateinit var res_val: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupListeners()
    }

    fun setupView() {
        btnCalc = findViewById(R.id.bt_calc)
        price = findViewById(R.id.et_price_km)
        trav = findViewById(R.id.et_traveled)
        res_text = findViewById(R.id.tv_result)
        res_val = findViewById(R.id.tv_result_val)
    }
    fun setupListeners() {
        btnCalc.setOnClickListener{
            val inpPrice = price.text.toString().toFloat()
            val inpTrav = trav.text.toString().toFloat()
            //Log.e("CAR_APP_INPUTS","Price: ${inpPrice}\nTraveled: ${inpTrav}")
            val result = inpPrice/inpTrav
            res_text.visibility = View.VISIBLE
            res_val.text = result.toString()
            res_val.visibility = View.VISIBLE

        }
    }
}
