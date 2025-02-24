package com.example.quartoappdio_eletriccar.domain.local

import android.provider.BaseColumns

object ContractCars {
    object CarEntry : BaseColumns {
        const val TABLE_NAME = "fav_cars"
        const val COLUMN_NAME_ID_LOCAL = "local_id"
        const val COLUMN_NAME_ID_EXTERNAL = "ext_id"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POWER = "power"
        const val COLUMN_NAME_CHARGE = "charge"
        const val COLUMN_NAME_URL = "url"
        const val COLUMN_NAME_FAVORITE = "favorite"
    }

    const val CREATE_CAR = "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
            "${CarEntry.COLUMN_NAME_ID_LOCAL} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${CarEntry.COLUMN_NAME_ID_EXTERNAL} INTEGER," +
            "${CarEntry.COLUMN_NAME_PRICE} varchar(255)," +
            "${CarEntry.COLUMN_NAME_BATTERY} varchar(255)," +
            "${CarEntry.COLUMN_NAME_POWER} varchar(255)," +
            "${CarEntry.COLUMN_NAME_CHARGE} varchar(255)," +
            "${CarEntry.COLUMN_NAME_URL} varchar(255)," +
            "${CarEntry.COLUMN_NAME_FAVORITE} boolean" +
            ");"

    const val DROP_CAR = "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME};"
}