package com.example.app_backlog20.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_backlog20.MainActivity
import com.example.app_backlog20.databinding.FragmentHomeBinding
import com.example.app_backlog20.ui.Databases.Juego
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val baseDatos:JuegosBaseDatos = JuegosBaseDatos.getBaseDatos(requireContext())
       // val lista = baseDatos.juegosDao().getAll()
        //for (i in lista) println(i)


        val datosFiltro = arrayOf("Todos","Pendiente", "Completado", "Aplazado", "Abandonado")
        val filtro =  ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, datosFiltro)
        filtro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFiltro.adapter = filtro
        var indOpcionFiltro:Int = binding.spinnerFiltro.selectedItemPosition
        var opcionFiltro:String = datosFiltro.get(indOpcionFiltro)





        var juegosTotales:Int? = baseDatos.juegosDao().totalJuegos()
        var juegosCompletados:Int? = baseDatos.juegosDao().filtroJuego("Completado")
        var juegosPlatinados:Int? = baseDatos.juegosDao().filtroJuego("Platinado") //Son los juegos que se ha obtenido el 100% de los trofeos
        var juegosPendientes:Int? = baseDatos.juegosDao().filtroJuego("Pendiente")
        var juegosAplazados:Int? = baseDatos.juegosDao().filtroJuego("Aplazado")
        var juegosAbandonados = baseDatos.juegosDao().filtroJuego("Abandonado")




        binding.juegosTotales.text = juegosTotales.toString()
        binding.juegosCompletados.text = juegosCompletados.toString()
        binding.juegosPlatinados.text = juegosPlatinados.toString()
        binding.juegosAplazados.text = juegosAplazados.toString()
        binding.juegosPendientes.text = juegosPendientes.toString()
        binding.juegosAbandonados.text = juegosAbandonados.toString()

        val homeList: RecyclerView = binding.recyclerView
        val homeAdapter = HomeAdapter()
        homeList.adapter = homeAdapter
        homeAdapter.juegos = baseDatos.juegosDao().getAll()
        homeList.layoutManager = LinearLayoutManager(requireContext())

        //Cuando le damos al botón de filtrar, aparecerán solo los juegos que cumplan las condiciones
        binding.buttonFiltro.setOnClickListener(){
            val estado:String = binding.spinnerFiltro.selectedItem.toString()
            if (estado=="Todos") homeAdapter.juegos = baseDatos.juegosDao().getAll()
            else homeAdapter.juegos = baseDatos.juegosDao().filtroEst(estado)

            homeList.layoutManager = LinearLayoutManager(requireContext())
        }
        return root




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}