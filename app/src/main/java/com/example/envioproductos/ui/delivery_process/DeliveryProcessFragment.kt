package com.example.envioproductos.ui.delivery_process
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.databinding.FragmentDeliveryProcessBinding
import com.example.envioproductos.cache.CacheManager
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random
import com.example.envioproductos.utils.Utils
/** Fragmento que simula el proceso de entrega de productos
 * Mostrando la temperatura (simulada) en tiempo real y un botón para finalizar*/
class DeliveryProcessFragment : Fragment(R.layout.fragment_delivery_process) {
    // ViewBinding para acceder a los elementos del layout de manera segura
    private var _binding: FragmentDeliveryProcessBinding? = null
    private val binding get() = _binding!!
    // Variables para la simulación de temperatura
    private var base = 5.0                            // Temperatura base inicial
    private val seed: Int = 42                        // Semilla para Random reproducible
    private val rng = Random(seed)                    // Generador de números aleatorios
    private val temperatureMaxLimit = 5              // Límite máximo de temperatura permitida

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializa el binding
        _binding = FragmentDeliveryProcessBinding.bind(view)
        // Mostrar la dirección de destino en el fragmento
        binding.deliveryProcessDestinyAddressText.text =
            (CacheManager.get("destinyAddressTitle") ?: "Ingresa dirección de destino ...") as String
        // Crear un timer para actualizar la temperatura cada cierto tiempo
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Script que se ejecuta periódicamente
                base = updatingTemperature()  // Actualiza la temperatura según el escenario
                // Modificar la UI desde el hilo principal
                requireActivity().runOnUiThread {
                    // Actualiza el texto de temperatura
                    view.findViewById<TextView>(R.id.delivery_process_temperature_text).text =
                        "${Utils.formatNumber(base, 2)} °C"
                    // Cambia el color del bloque según temperatura
                    if(base < temperatureMaxLimit) {
                        view.findViewById<LinearLayout>(R.id.delivery_process_temperature_block)
                            .setBackgroundResource(R.drawable.border_layout_green)
                    } else {
                        view.findViewById<LinearLayout>(R.id.delivery_process_temperature_block)
                            .setBackgroundResource(R.drawable.border_layout_red)
                    }
                }
            }
        }, 0, 1_000) // Se ejecuta cada 1 segundo (1_000 ms)

        // Botón para finalizar el envío
        view.findViewById<Button>(R.id.delivery_process_continue_button).setOnClickListener {
            Toast.makeText(requireContext(), "Envío finalizado", Toast.LENGTH_SHORT).show()
            CacheManager.clear() // Limpiar caché al finalizar
            findNavController().navigate(R.id.nav_products) // Volver al listado de productos
        }
    }

    /**Función para simular la temperatura del camión,
     * entrega una temperatura aleatoria alrededor de una cantidad inicial*/
    fun updatingTemperature(): Double {
        val value = base + rng.nextDouble(-0.5, 0.5)
        return value
    }
}
