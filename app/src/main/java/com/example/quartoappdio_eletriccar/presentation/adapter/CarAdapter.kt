package com.example.quartoappdio_eletriccar.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import com.example.quartoappdio_eletriccar.R
import com.example.quartoappdio_eletriccar.domain.Car

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.charge.text = cars[position].charge
        val photoUrl = cars[position].urlPhoto
        holder.photo.load(photoUrl) {
            listener(object : ImageRequest.Listener {
                override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                    super.onSuccess(request, result)
                    Thread.sleep(1000)
                    setVisibility(holder)
                }
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    super.onError(request, result)
                    val c : Context = holder.itemView.context
                    val errorDraw : Drawable? = ContextCompat.getDrawable(c, R.drawable.error_01)
                    holder.photo.setImageDrawable(errorDraw)
                    setVisibility(holder)
                }
            })
        }
    }

    private fun setVisibility(holder: ViewHolder) {
        holder.pBar.visibility = View.GONE
        holder.photo.visibility = View.VISIBLE
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val charge: TextView
        val photo: ImageView
        val pBar: ProgressBar

        init {
            view.apply {
                price = findViewById(R.id.tv_price_val)
                battery = findViewById(R.id.tv_battery_val)
                power = findViewById(R.id.tv_power_val)
                charge = findViewById(R.id.tv_charge_val)
                photo = findViewById(R.id.iv_photo)
                pBar = findViewById(R.id.pb_load_photo)
            }
        }
    }

}

