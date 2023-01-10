package com.example.app_backlog20.ui.Game

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
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

        val listNombre:MutableList<String> = baseDatos.juegosDao().listNombre()
        listNombre.add(0,"Escoge un juego...")
        val nombres = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, listNombre)
        nombres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nombres.isEnabled(0)
        binding.spinNombre.adapter = nombres

        val datosPlataforma =
            arrayOf("Escoge la plataforma...","PlayStation 4","Xbox One", "Nintendo Switch")
        val plataforma =
            ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosPlataforma)
        plataforma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        plataforma.isEnabled(0)
        binding.spinPlataforma.adapter = plataforma



        val datosEstado = arrayOf("Escoge un estado...","Pendiente","Platinado", "Completado", "Aplazado", "Abandonado")
        val estado = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosEstado)
        estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estado.isEnabled(0)

        binding.spinEstado.adapter = estado


        val datosFormato = arrayOf("Escoge un formato...", "Físico", "Digital", "Suscripción")
        val formato = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, datosFormato)
        formato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        formato.isEnabled(0)
        binding.spinFormato.adapter = formato



        binding.editGameButton.setOnClickListener() {
            val nombre = binding.spinNombre.selectedItem.toString()
            val datoPlataforma: String = binding.spinPlataforma.selectedItem.toString()
            val datoEstado: String = binding.spinEstado.selectedItem.toString()
            val datoFormato: String = binding.spinFormato.selectedItem.toString()
            val juego = Juego(nombre, datoPlataforma, datoEstado, datoFormato)
            if (baseDatos.juegosDao().busqueda(nombre, datoPlataforma) == null) {
                Toast.makeText(requireContext(), "El juego no está registrado", Toast.LENGTH_SHORT).show()
            } else {
                if (datoEstado == datosEstado.get(0) || nombre == nombres.getItem(0) ||
                    datoPlataforma == datosPlataforma.get(0) || datoFormato == datosFormato.get(0)
                ) {
                    Toast.makeText(requireContext(), "Los datos no son válidos", Toast.LENGTH_SHORT).show()
                } else {
                    baseDatos.juegosDao()
                        .update(juego) //Actualizamos los datos del objeto de la BBDD
                    binding.spinNombre.setSelection(0)
                    binding.spinEstado.setSelection(0)
                    binding.spinPlataforma.setSelection(0)
                    binding.spinFormato.setSelection(0)
                    Toast.makeText(
                        requireContext(),
                        "Se han actualizado los datos",
                        Toast.LENGTH_SHORT).show()

                }
            }
        }
        return root
    }
}