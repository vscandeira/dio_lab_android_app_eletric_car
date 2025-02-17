package com.example.quartoappdio_eletriccar.data

import com.example.quartoappdio_eletriccar.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("api/cars/")
    fun getAllCars() : Call<List<Car>>
}