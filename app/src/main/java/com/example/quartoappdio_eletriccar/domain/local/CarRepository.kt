package com.example.quartoappdio_eletriccar.domain.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.quartoappdio_eletriccar.domain.Car
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_BATTERY
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_CHARGE
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_FAVORITE
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_ID_EXTERNAL
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_ID_LOCAL
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_POWER
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_PRICE
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.COLUMN_NAME_URL
import com.example.quartoappdio_eletriccar.domain.local.ContractCars.CarEntry.TABLE_NAME

class CarRepository(c: Context) {
    private val context = c
    private val dbHelper = CarsDBHelper(context)
    private val db = dbHelper.writableDatabase

    private fun persist (car : Car, setFav : Boolean, update : Boolean): Boolean {
        var inserted : Long? = null
        try {
            val valuesNew = ContentValues().apply {
                put(COLUMN_NAME_ID_EXTERNAL, car.id)
                put(COLUMN_NAME_PRICE, car.price)
                put(COLUMN_NAME_BATTERY, car.battery)
                put(COLUMN_NAME_POWER, car.power)
                put(COLUMN_NAME_CHARGE, car.charge)
                put(COLUMN_NAME_URL, car.urlPhoto)
                put(COLUMN_NAME_FAVORITE, setFav)
            }
            val valuesFav = ContentValues().apply {
                put(COLUMN_NAME_FAVORITE, setFav)
            }
            if (update) {
                inserted = db?.update(TABLE_NAME, valuesFav, "${COLUMN_NAME_ID_EXTERNAL} = ?", arrayOf(car.id.toString()))?.toLong()
            } else {
                inserted = db?.insert(TABLE_NAME, null, valuesNew)
            }
        } catch(e : Exception) {
            Log.e("DEBUG", "Error accessing database\n $e")
        }
        return inserted != null
    }

    private fun findById(id : Long) : Car? {
        val cursor : Cursor? = db?.rawQuery("SELECT * FROM ${TABLE_NAME} WHERE ${COLUMN_NAME_ID_EXTERNAL} = ?", arrayOf(id.toString()))
        var car : Car? = null
        cursor?.use {
            if (it.moveToFirst()){
                val model = Car(
                    id = it.getLong(it.getColumnIndexOrThrow(COLUMN_NAME_ID_EXTERNAL)).toInt(),
                    price = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_PRICE)),
                    battery = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_BATTERY)),
                    power = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_POWER)),
                    charge = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_CHARGE)),
                    urlPhoto = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_URL)),
                    isFavorite = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_FAVORITE)) == 1
                )
                car = model
            }
        }
        return car
    }

    private fun changeFavorite(car : Car, fav : Boolean) : Boolean {
        return persist(car, fav, true)
    }

    fun save (car : Car): Boolean {
        val carOnDatabase = findById(car.id.toLong())
        if (carOnDatabase == null){
            Log.d("DEBUG", "Car ${car.id} is new and being saved for the first time")
            return persist(car, true, false)
        } else {
            Log.d("DEBUG", "Car ${car.id} is not new. Its current flag is ${carOnDatabase.isFavorite} and its new flag will be ${!carOnDatabase.isFavorite}")
            return changeFavorite(car, !carOnDatabase.isFavorite)
        }
    }

    fun getFavorite(car : Car) : Boolean? {
        val carOnDatabase = findById(car.id.toLong())
        return carOnDatabase?.isFavorite
    }

    fun getFavCars() : List<Car> {
        val cursor : Cursor? = db?.rawQuery("SELECT * FROM ${TABLE_NAME} WHERE ${COLUMN_NAME_FAVORITE} = 1", arrayOf())
        val cars : MutableList<Car> = mutableListOf()
        cursor?.use {
            if (it.moveToFirst()){
                do {
                    val model = Car(
                        id = it.getLong(it.getColumnIndexOrThrow(COLUMN_NAME_ID_EXTERNAL)).toInt(),
                        price = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_PRICE)),
                        battery = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_BATTERY)),
                        power = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_POWER)),
                        charge = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_CHARGE)),
                        urlPhoto = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_URL)),
                        isFavorite = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_FAVORITE)) == 1
                    )
                    cars.add(model)
                } while (it.moveToNext())
            }
        }
        return cars
    }

    fun deleteCar(car: Car) : Boolean {
        if (findById(car.id.toLong()) != null){
            db?.delete(TABLE_NAME, "${COLUMN_NAME_ID_EXTERNAL} = ?", arrayOf(car.id.toString()))
            return true
        } else {
            Log.d("DEBUG", "Car ${car.id} doen't exists on local database")
            return false
        }
    }

}