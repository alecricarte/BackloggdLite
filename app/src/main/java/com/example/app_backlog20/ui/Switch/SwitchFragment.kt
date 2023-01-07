package com.example.app_backlog20.ui.Switch

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_backlog20.databinding.FragmentSwitchBinding
import com.example.app_backlog20.databinding.FragmentXboxBinding
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos
import com.example.app_backlog20.ui.XBOX.XOneAdapter

class SwitchFragment : Fragment() {

    private var _binding: FragmentSwitchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSwitchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseDatos: JuegosBaseDatos = JuegosBaseDatos.getBaseDatos(requireContext())

        val datosFiltro = arrayOf("Todos", "Pendiente", "Completado", "Platinado", "Aplazado", "Abandonado")
        val filtro =  ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFiltro)
        filtro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFiltro.adapter = filtro
        var indOpcionFiltro:Int = binding.spinnerFiltro.selectedItemPosition
        var opcionFiltro:String = datosFiltro.get(indOpcionFiltro)




        var juegosTotales:Int? = baseDatos.juegosDao().filtroPlatCont("Nintendo Switch")
        var juegosCompletados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Completado", "Nintendo Switch")
        var juegosPlatinados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Platinado", "Nintendo Switch") //Son los juegos que se ha obtenido el 100% de los trofeos
        var juegosPendientes:Int? = baseDatos.juegosDao().filtroJuegoPlat("Pendiente", "Nintendo Switch")
        var juegosAplazados:Int? = baseDatos.juegosDao().filtroJuegoPlat("Aplazado", "Nintendo Switch")
        var juegosAbandonados = baseDatos.juegosDao().filtroJuegoPlat("Abandonado", "Nintendo Switch")


        binding.juegosTotales.text = juegosTotales.toString()
        binding.juegosCompletados.text = juegosCompletados.toString()
        binding.juegosPlatinados.text = juegosPlatinados.toString()
        binding.juegosAplazados.text = juegosAplazados.toString()
        binding.juegosPendientes.text = juegosPendientes.toString()
        binding.juegosAbandonados.text = juegosAbandonados.toString()

        val switchList: RecyclerView = binding.recyclerView
        val switchAdapter = XOneAdapter()
        switchList.adapter = switchAdapter
        switchAdapter.juegos = baseDatos.juegosDao().filtroPlat("Nintendo Switch")
        switchList.layoutManager = LinearLayoutManager(requireContext())

        //Cuando le damos al botón de filtrar, aparecerán solo los juegos que cumplan las condiciones
        binding.buttonFiltro.setOnClickListener(){
            val estado:String = binding.spinnerFiltro.selectedItem.toString()
            if (estado=="Todos") switchAdapter.juegos = baseDatos.juegosDao().getAll()
            else switchAdapter.juegos = baseDatos.juegosDao().filtroEst(estado)

            switchList.layoutManager = LinearLayoutManager(requireContext())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}