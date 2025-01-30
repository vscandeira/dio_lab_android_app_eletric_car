package com.example.quartoappdio_eletriccar.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.domain.Car

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Log.d("DEBUG","onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //Log.d("DEBUG","getItemCount called getting ${cars.size} items")
        return cars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("DEBUG","onBindViewHolder called with position $position")
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.charge.text = cars[position].charge
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val charge: TextView

        init {
            view.apply {
                //Log.d("DEBUG","ViewHolder.init called")
                price = findViewById(R.id.tv_price_val)
                battery = findViewById(R.id.tv_battery_val)
                power = findViewById(R.id.tv_power_val)
                charge = findViewById(R.id.tv_charge_val)
            }
        }
    }

}

