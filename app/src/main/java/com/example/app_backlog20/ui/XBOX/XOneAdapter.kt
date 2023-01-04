package com.example.app_backlog20.ui.XBOX

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_backlog20.R
import com.example.app_backlog20.ui.Databases.Juego

class XOneAdapter: RecyclerView.Adapter<XOneAdapter.XOneViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XOneViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recycler_xone, parent, false)
        return XOneViewHolder(view)    }

    override fun onBindViewHolder(holder: XOneViewHolder, position: Int) {
        val juego = juegos[position]
        holder.bind(juego)
    }

    override fun getItemCount() = juegos.size

    class XOneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre_juego)
        val plataforma: TextView = view.findViewById(R.id.plataforma_juego)
        val estado: TextView = view.findViewById(R.id.estado_juego)
        val formato: TextView = view.findViewById(R.id.formato_juego)

        fun bind(juego: Juego) {
            nombre.text = juego.nombre
            plataforma.text = juego.plataforma
            estado.text = juego.estado
            formato.text = juego.formato

        }
    }

    var juegos = listOf<Juego>()
        //La lista comienza vacía pero personalizamos su set
        // para actualizar el campo de respaldo y luego invocar a notifyDataSetChanged()
        // que notifica al adaptador que los datos han cambiado y así redibujar
        // todos los elementos de la lista
        set(value) {
            field = value
            notifyDataSetChanged()
        }
}