package com.example.quartoappdio_eletriccar.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.quartoappdio_eletriccar.R
import java.text.NumberFormat
import java.util.Locale

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
        setCachedResult()
    }

    override fun onResume() {
        super.onResume()
        setCachedResult()
    }

    private fun setupView() {
        btnCalc = findViewById(R.id.bt_calc)
        btnGoBack = findViewById(R.id.iv_left_arrow)
        price = findViewById(R.id.et_price_km)
        trav = findViewById(R.id.et_traveled)
        res_text = findViewById(R.id.tv_result)
        res_val = findViewById(R.id.tv_result_val)
    }
    private fun setupListeners() {
        btnCalc.setOnClickListener{
            val result = calculate()
            setResult(result)

        }

        btnGoBack.setOnClickListener{
            //primeira forma empilha mais uma activity e consome mais memória
            //startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setCachedResult() {
        val calculatedBef = getSharedPref()
        if (calculatedBef > 0.0) {
            setResult(calculatedBef)
        }
    }
    private fun setResult(result: Float){
        res_text.visibility = View.VISIBLE
        res_val.text = formatNumber(result)
        res_val.visibility = View.VISIBLE
    }

    private fun formatNumber (result : Float) : String {
        val numberFormat = NumberFormat.getInstance(Locale("en", "US"))
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2

        return numberFormat.format(result)
    }

    private fun calculate(): Float {
        val inpPrice = price.text.toString().toFloat()
        val inpTrav = trav.text.toString().toFloat()

        val result = inpPrice/inpTrav
        saveSharedPref(result)
        return result
    }

    private fun saveSharedPref(result : Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), result)
            apply()
        }
    }

    private fun getSharedPref() : Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val calc = sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
        return calc
    }

}