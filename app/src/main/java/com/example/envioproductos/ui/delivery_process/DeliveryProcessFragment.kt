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
import com.example.envioproductos.ui.delivery_process.SimulateTemperature.Scenario
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random
import com.example.envioproductos.utils.Utils



enum class Scenario { STABLE_COLD, DRIFT_WARMING, SPIKES }
class DeliveryProcessFragment : Fragment(R.layout.fragment_delivery_process) {
    private var _binding: FragmentDeliveryProcessBinding? = null
    private val binding get() = _binding!!

    private var base = 5.0
    private val scenario: Scenario = Scenario.STABLE_COLD
    private val seed: Int = 42
    private val rng = Random(seed)

    private val temperatureMaxLimit = 5

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDeliveryProcessBinding.bind(view)

        binding.deliveryProcessDestinyAddressText.text = (CacheManager.get("destinyAddressTitle") ?: "Ingresa dirección de destino ...") as String



        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // 👉 Script a ejecutar
                println("Ejecutando script cada 30 segundos")
                base = updatingTemperature()
                requireActivity().runOnUiThread {
                    // 👉 Aquí ya puedes modificar la UI
                    view.findViewById<TextView>(R.id.delivery_process_temperature_text).text = "${Utils.formatNumber(base, 2)} °C"
                    if(base < temperatureMaxLimit) {
                        view.findViewById<LinearLayout>(R.id.delivery_process_temperature_block).setBackgroundResource(R.drawable.border_layout_green)
                    } else {
                        view.findViewById<LinearLayout>(R.id.delivery_process_temperature_block).setBackgroundResource(R.drawable.border_layout_red)
                    }
                }
            }
        }, 0, 1_000)

        view.findViewById<Button>(R.id.delivery_process_continue_button).setOnClickListener {
            Toast.makeText(requireContext(), "Envío finalizado", Toast.LENGTH_SHORT).show()
            CacheManager.clear()
            findNavController().navigate(R.id.nav_products)
        }

    }

    fun updatingTemperature () : Double {
        val value = when (scenario) {
            Scenario.STABLE_COLD ->
                base + rng.nextDouble(-0.5, 0.5)
            Scenario.DRIFT_WARMING -> {
                base += 0.3                      // se va calentando
                base + rng.nextDouble(-0.6, 0.6)
            }
            Scenario.SPIKES -> {
                val spike = if (rng.nextDouble() < 0.15) 6.0 else 0.0
                base + rng.nextDouble(-0.7, 0.7) + spike
            }
            else -> {
                base
            }
        }
        return value
    }
}