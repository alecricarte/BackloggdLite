package com.example.app_backlog20.ui.Databases

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface JuegosDao {
    @Query("SELECT * from Juego")
    fun getAll(): List<Juego>

    @Query("SELECT COUNT (*) FROM juego")
    fun totalJuegos(): Int?

    @Query ("SELECT COUNT (*) from juego WHERE estado LIKE (:estado)")
    fun filtroJuego (estado: String): Int?

    @Query ("SELECT * from juego WHERE plataforma LIKE (:plataforma)")
    fun filtroPlat (plataforma: String): List<Juego>

    @Query ("SELECT COUNT (*) FROM juego WHERE plataforma LIKE (:plataforma)")
    fun filtroPlatCont (plataforma: String): Int?

    @Query ("SELECT COUNT (*) from juego WHERE estado LIKE (:estado)" + " AND plataforma LIKE (:plataforma)")
    fun filtroJuegoPlat (estado: String, plataforma: String): Int?

    @Query ("SELECT * FROM juego WHERE estado LIKE (:estado)")
    fun filtroEst (estado:String): List<Juego>

    @Query ("SELECT * FROM juego WHERE nombre LIKE (:nombre)" + "AND plataforma LIKE (:plataforma)")
    fun busqueda (nombre:String, plataforma: String):Juego

    @Insert(onConflict = IGNORE)
    fun insert(juego: Juego)

    @Update
    fun update(juego: Juego)

    @Delete
    fun delete(juego: Juego)

}




