package com.example.app_backlog20.ui.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Juego::class], version = 1)
abstract class JuegosBaseDatos : RoomDatabase() {
    abstract fun juegosDao(): JuegosDao

    companion object {
        @Volatile private var INSTANCE: JuegosBaseDatos? = null
        private const val NOMBRE_BASEDATOS = "juegos_basedatos"

        // Creamos una BBDD a la que accederán todas las vistas
        fun getBaseDatos(context:Context): JuegosBaseDatos {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            //Todas las vistas accederán a la misma BBDD
            synchronized(this){
                val instance = Room.databaseBuilder(context,
                JuegosBaseDatos::class.java, NOMBRE_BASEDATOS).allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}