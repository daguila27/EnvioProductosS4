package com.example.envioproductos.ui.delivery_process

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.envioproductos.R
import com.example.envioproductos.databinding.FragmentDeliveryProcessBinding

class DeliveryProcessFragment : Fragment(R.layout.fragment_delivery_process) {
    private var _binding: FragmentDeliveryProcessBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDeliveryProcessBinding.bind(view)
        //val sensor = SimulateTemperature(
        //    intervalMs = 1200,
        //    scenario = SimulateTemperature.Scenario.DRIFT_WARMING
        //)
        //val vm = TemperatureViewModel(sensor, limitCelsius = -18.0)
        /**
        lifecycleScope.launchWhenStarted {
            vm.state.collect { s ->
                binding.deliveryProcessTemperatureText.text = s.temperature+" °C"
                if (s.isAlarm) {
                    binding.alarmBadge.isVisible = true
                    binding.alarmText.text = "¡ALARMA! Temperatura sobre el límite"
                    // aquí puedes bloquear despacho de congelados, notificar, etc.
                } else {
                    binding.alarmBadge.isVisible = false
                    binding.alarmText.text = "En rango seguro"
                }
            }
        }
         **/

    }


}