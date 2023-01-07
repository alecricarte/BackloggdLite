package com.example.app_backlog20.ui.XBOX

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_backlog20.databinding.FragmentXboxBinding
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos
import com.example.app_backlog20.ui.Ps4.Ps4Adapter

class XBOXFragment : Fragment() {

    private var _binding: FragmentXboxBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentXboxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseDatos: JuegosBaseDatos = JuegosBaseDatos.getBaseDatos(requireContext())

        val datosFiltro = arrayOf("Pendiente", "Completado", "Platinado", "Aplazado", "Abandonado")
        val filtro =  ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFiltro)
        filtro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFiltro.adapter = filtro
        var indOpcionFiltro:Int = binding.spinnerFiltro.selectedItemPosition
        var opcionFiltro:String = datosFiltro.get(indOpcionFiltro)




        var juegosTotales:Int? = baseDatos.juegosDao().filtroPlatCont("Xbox One")
        var juegosCompletados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Completado", "Xbox One")
        var juegosPlatinados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Platinado", "Xbox One") //Son los juegos que se ha obtenido el 100% de los trofeos
        var juegosPendientes:Int? = baseDatos.juegosDao().filtroJuegoPlat("Pendiente", "Xbox One")
        var juegosAplazados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Aplazado", "Xbox One")
        var juegosAbandonados = baseDatos.juegosDao().filtroJuegoPlat("Abandonado", "Xbox One")


        binding.juegosTotales.text = juegosTotales.toString()
        binding.juegosCompletados.text = juegosCompletados.toString()
        binding.juegosPlatinados.text = juegosPlatinados.toString()
        binding.juegosAplazados.text = juegosAplazados.toString()
        binding.juegosPendientes.text = juegosPendientes.toString()
        binding.juegosAbandonados.text = juegosAbandonados.toString()

        val xoneList: RecyclerView = binding.recyclerView
        val xoneAdapter = XOneAdapter()
        xoneList.adapter = xoneAdapter
        xoneAdapter.juegos = baseDatos.juegosDao().filtroPlat("Xbox One")
        xoneList.layoutManager = LinearLayoutManager(requireContext())

        //Cuando le damos al botón de filtrar, aparecerán solo los juegos que cumplan las condiciones
        binding.buttonFiltro.setOnClickListener(){
            val estado:String = binding.spinnerFiltro.selectedItem.toString()
            if (estado=="Todos") xoneAdapter.juegos = baseDatos.juegosDao().getAll()
            else xoneAdapter.juegos = baseDatos.juegosDao().filtroEst(estado)

            xoneList.layoutManager = LinearLayoutManager(requireContext())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}