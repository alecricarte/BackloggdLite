package com.example.app_backlog20.ui.Game

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.app_backlog20.databinding.FragmentGameDeleteBinding
import com.example.app_backlog20.ui.Databases.Juego
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos

class GameDeleteFragment: Fragment() {
    private var _binding: FragmentGameDeleteBinding? = null

// This property is only valid between onCreateView and
// onDestroyView.

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentGameDeleteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseDatos = JuegosBaseDatos.getBaseDatos(requireContext())

        val nombres = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, baseDatos.juegosDao().listNombre())
        nombres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinNombre.adapter = nombres

        val datosPlataforma =
            arrayOf("PlayStation 4", "Xbox One", "Nintendo Switch")
        val plataforma =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosPlataforma)
        plataforma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinPlataforma.adapter = plataforma



        binding.deleteGameButton.setOnClickListener() {
            val nombre = binding.spinNombre.selectedItem.toString()
            val datoPlataforma: String = binding.spinPlataforma.selectedItem.toString()
            val juego = baseDatos.juegosDao().busqueda(nombre, datoPlataforma)
            baseDatos.juegosDao().delete(juego)
        }
        return root
    }
}