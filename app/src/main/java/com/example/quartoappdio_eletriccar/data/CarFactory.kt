package com.example.quartoappdio_eletriccar.data

import com.example.quartoappdio_eletriccar.domain.Car

object CarFactory {
    public val listCars = listOf<Car> ( Car(
        id = 1,
        price = "$ 51.000,00",
        battery = "93.4 kWh",
        power = "646 cv",
        charge = "25 min",
        urlPhoto = "https://www.google.com/"),
        Car(
        id = 2,
        price = "$ 52.000,00",
        battery = "93.4 kWh",
        power = "646 cv",
        charge = "25 min",
        urlPhoto = "https://www.google.com/")
    )
}