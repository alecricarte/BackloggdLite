package com.example.app_backlog20.ui.Ps4

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_backlog20.databinding.FragmentPs4Binding
import com.example.app_backlog20.ui.Databases.Juego
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos
import com.example.app_backlog20.ui.home.HomeAdapter


class Ps4Fragment : Fragment() {

    private var _binding: FragmentPs4Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentPs4Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val baseDatos: JuegosBaseDatos = JuegosBaseDatos.getBaseDatos(requireContext())

        val datosFiltro = arrayOf("Pendiente", "Completado", "Platinado", "Aplazado", "Abandonado")
        val filtro =  ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFiltro)
        filtro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFiltro.adapter = filtro
        var indOpcionFiltro:Int = binding.spinnerFiltro.selectedItemPosition
        var opcionFiltro:String = datosFiltro.get(indOpcionFiltro)

        var plataforma = "PlayStation 4"
        var juegosTotales:Int? = baseDatos.juegosDao().filtroPlatCont("PlayStation 4")
        var juegosCompletados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Completado", "PlayStation 4")
        var juegosPlatinados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Platinado", "PlayStation 4") //Son los juegos que se ha obtenido el 100% de los trofeos
        var juegosPendientes:Int? = baseDatos.juegosDao().filtroJuegoPlat("Pendiente", "PlayStation 4")
        var juegosAplazados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Aplazado", "PlayStation 4")
        var juegosAbandonados = baseDatos.juegosDao().filtroJuegoPlat("Abandonado", "PlayStation 4")


        binding.nombreUsuario.text = plataforma
        binding.juegosTotales.text = juegosTotales.toString()
        binding.juegosCompletados.text = juegosCompletados.toString()
        binding.juegosPlatinados.text = juegosPlatinados.toString()
        binding.juegosAplazados.text = juegosAplazados.toString()
        binding.juegosPendientes.text = juegosPendientes.toString()
        binding.juegosAbandonados.text = juegosAbandonados.toString()

        val ps4List: RecyclerView = binding.recyclerView
        val ps4Adapter = Ps4Adapter()
        ps4List.adapter = ps4Adapter
        ps4Adapter.juegos = baseDatos.juegosDao().filtroPlat("PlayStation 4")
        ps4List.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonFiltro.setOnClickListener(){
            val estado:String = binding.spinnerFiltro.selectedItem.toString()
            if (estado=="Todos") ps4Adapter.juegos = baseDatos.juegosDao().getAll()
            else ps4Adapter.juegos = baseDatos.juegosDao().filtroEst(estado)

            ps4List.layoutManager = LinearLayoutManager(requireContext())
        }
        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}