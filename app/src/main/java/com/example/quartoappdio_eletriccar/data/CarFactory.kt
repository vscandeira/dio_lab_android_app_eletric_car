package com.example.quartoappdio_eletriccar.data

import com.example.quartoappdio_eletriccar.domain.Car

class CarFactory {
    val list = listOf<Car> ( Car(
        id = 1,
        price = "$ 50.000,00",
        battery = "93.4 kWh",
        power = "646 cv",
        charge = "25 min",
        urlPhoto = "https://www.google.com/"
    ))
}