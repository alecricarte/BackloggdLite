package com.example.app_backlog20.ui.Game

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
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
        //Le damos los datos al spinner de juego
        var listNombre:MutableList<String> = baseDatos.juegosDao().listNombre() // MutableList para poder editarla
        listNombre.add(0,"Escoge un juego...") //Añadimos un valor por defecto
        var nombres = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, listNombre)
        nombres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinNombre.adapter = nombres
        // Le damos los datos al spinner de plataforma
        val datosPlataforma =
            arrayOf("Escoge la plataforma","PlayStation 4", "Xbox One", "Nintendo Switch")
        val plataforma =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosPlataforma)
        plataforma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinPlataforma.adapter = plataforma


        // Cuando pulsemos el botón se eliminará el juego de la base de datos, excepto si algún dato no se ha rellenado
        binding.deleteGameButton.setOnClickListener() {
            val nombre = binding.spinNombre.selectedItem.toString()
            val datoPlataforma: String = binding.spinPlataforma.selectedItem.toString()
            val juego = baseDatos.juegosDao().busqueda(nombre, datoPlataforma) //Buscamos el juego en la BBDD
            if ( nombre == nombres.getItem(0) || datoPlataforma == datosPlataforma.get(0)) {
                Toast.makeText(requireContext(), "Los datos no son válidos", Toast.LENGTH_SHORT)

            }
            else {
                baseDatos.juegosDao().delete(juego) //Eliminamos el juego de la BBDD
                listNombre =baseDatos.juegosDao().listNombre()
                listNombre.add(0,"Escoge un juego...") //Añadimos el valor por defecto nuevamente
                nombres = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, listNombre)
                nombres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinNombre.adapter = nombres
                binding.spinNombre.setSelection(0)
                binding.spinPlataforma.setSelection(0)
                Toast.makeText(requireContext(), "Se han actualizado los datos", Toast.LENGTH_SHORT)

            }
        }

        return root
    }
}