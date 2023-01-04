package com.example.app_backlog20.ui.Game

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.app_backlog20.databinding.FragmentGameEditBinding
import com.example.app_backlog20.ui.Databases.Juego
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos

class GameEditFragment: Fragment() {
    private var _binding: FragmentGameEditBinding? = null

// This property is only valid between onCreateView and
// onDestroyView.

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentGameEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseDatos = JuegosBaseDatos.getBaseDatos(requireContext())


        val datosPlataforma =
            arrayOf("PlayStation 4", "PlayStation 5", "Xbox One", "Xbox Series", "Nintendo Switch")
        val plataforma =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosPlataforma)
        plataforma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinPlataforma.adapter = plataforma


        val datosEstado = arrayOf("Pendiente", "Completado", "Aplazado", "Abandonado")
        val estado = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosEstado)
        estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinEstado.adapter = estado


        val datosFormato = arrayOf("Físico", "Digital", "Suscripción")
        val formato = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFormato)
        formato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinFormato.adapter = formato



        binding.editGameButton.setOnClickListener() {
            val nombre = binding.editTextNombre.text.toString().trim()
            val datoPlataforma: String = binding.spinPlataforma.selectedItem.toString()
            val datoEstado: String = binding.spinEstado.selectedItem.toString()
            val datoFormato: String = binding.spinFormato.selectedItem.toString()
            val juego = Juego(nombre, datoPlataforma, datoEstado, datoFormato)
            baseDatos.juegosDao().update(juego)
            binding.editTextNombre.getText().clear()
        }
        return root
    }
}