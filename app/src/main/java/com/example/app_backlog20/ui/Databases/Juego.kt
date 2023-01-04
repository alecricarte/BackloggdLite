package com.example.app_backlog20.ui.Databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["nombre", "plataforma"])
data class Juego (
    @ColumnInfo(name = "nombre") val nombre:String,
    @ColumnInfo(name = "plataforma")val plataforma:String,
    @ColumnInfo(name = "estado")val estado:String,
    @ColumnInfo(name = "formato")val formato:String,


        ){
    constructor(juego: Juego) : this(juego.nombre!!,
        juego.plataforma!!, juego.estado, juego.formato)
}

