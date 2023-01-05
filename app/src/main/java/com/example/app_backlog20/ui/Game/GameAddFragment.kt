package com.example.app_backlog20.ui.Game

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app_backlog20.databinding.FragmentGameAddBinding
import com.example.app_backlog20.ui.Databases.Juego
import com.example.app_backlog20.ui.Databases.JuegosBaseDatos


class GameAddFragment: Fragment() {
    private var _binding: FragmentGameAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentGameAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val baseDatos = JuegosBaseDatos.getBaseDatos(requireContext())


        val datosPlataforma =
            arrayOf("Escoge la plataforma...","PlayStation 4","Xbox One", "Nintendo Switch")
        val plataforma =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosPlataforma)
        plataforma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        plataforma.isEnabled(0)
        binding.spinPlataforma.adapter = plataforma



        val datosEstado = arrayOf("Escoge un estado...","Pendiente", "Completado", "Aplazado", "Abandonado")
        val estado = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosEstado)
        estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estado.isEnabled(0)

        binding.spinEstado.adapter = estado


        val datosFormato = arrayOf("Escoge un formato...", "Físico", "Digital", "Suscripción")
        val formato = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFormato)
        formato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        formato.isEnabled(0)
        binding.spinFormato.adapter = formato



        binding.addGameButton.setOnClickListener(){
            val nombre = binding.editTextNombre.text.toString().trim()
            val datoPlataforma:String = binding.spinPlataforma.selectedItem.toString()
            val datoEstado:String = binding.spinEstado.selectedItem.toString()
            val datoFormato:String = binding.spinFormato.selectedItem.toString()
            val juego = Juego(nombre, datoPlataforma, datoEstado, datoFormato)

            if (datoEstado == datosEstado.get(0) || nombre == "" || datoPlataforma == datosPlataforma.get(0)
                || datoFormato == datosFormato.get(0)) {
                Toast.makeText(requireContext(), "Los datos no son validos", Toast.LENGTH_SHORT)
            }else{
                baseDatos.juegosDao().insert(juego)
                binding.editTextNombre.getText().clear()
                binding.spinEstado.setSelection(0)
                binding.spinPlataforma.setSelection(0)
                binding.spinFormato.setSelection(0)
                Toast.makeText(requireContext(), "Se ha insertado el juego", Toast.LENGTH_SHORT)

            }


        }
        return root
    }
}