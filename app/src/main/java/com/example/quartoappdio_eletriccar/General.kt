package com.example.quartoappdio_eletriccar

import android.content.Context
import com.example.quartoappdio_eletriccar.domain.Car
import com.example.quartoappdio_eletriccar.domain.local.CarRepository

class General {
    companion object {
        fun formatString (inFront : Boolean, numDecimals : Int, formatSTR: String, number : String?) : String? {
            if(number == null) return null
            val prov = number.split(".")[0]
            val first = if (prov.length >= 4) prov.substring(0,prov.length-3) + "," +
                    prov.substring(prov.length-3,prov.length) else prov
            val second = number.split(".")[1]
            val ret = when {
                numDecimals == 1 -> first + "." + second[0]
                numDecimals == 2 -> first + "."  + second.substring(0,2)
                else -> first
            }
            if (inFront) {
                return formatSTR + ret
            } else {
                return ret + formatSTR
            }
        }

        fun treatCarsStrings(oldCarList : List<Car>, context : Context) : List<Car> {
            val newCarList : MutableList<Car> = mutableListOf()
            for (car in oldCarList) {
                val model = Car(
                    id = car.id,
                    price = formatString(true, 2, "$ ", car.price) ?: "-",
                    battery = formatString(false, 1, " kWh", car.battery) ?: "-",
                    power = formatString(false, 0, " cv", car.power) ?: "-",
                    charge = formatString(false, 0, " min", car.charge) ?: "-",
                    urlPhoto = car.urlPhoto,
                    isFavorite = CarRepository(context).getFavorite(car) ?: false
                )
                newCarList.add(model)
            }
            return newCarList
        }
    }
}