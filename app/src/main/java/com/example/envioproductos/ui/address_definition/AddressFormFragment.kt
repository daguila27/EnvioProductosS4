package com.example.envioproductos.ui.address_definition

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.databinding.FragmentAddressFormBinding
import com.example.envioproductos.ui.address_definition.address.Address
import com.example.envioproductos.utils.Utils
import kotlin.math.*


class AddressFormFragment : Fragment(R.layout.fragment_address_form)  {
    private var _binding: FragmentAddressFormBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddressFormBinding.bind(view)

        val originInputLayout = view.findViewById<LinearLayout>(R.id.form_address_origin_input)
        originInputLayout.setOnClickListener {
            val bundle = Bundle().apply {
                putString("cacheAddressKey", "originAddress")
            }
            findNavController().navigate(R.id.nav_google_map_input, bundle)
        }

        val destinyInputLayout = view.findViewById<LinearLayout>(R.id.form_address_destiny_input)
        destinyInputLayout.setOnClickListener {
            val bundle = Bundle().apply {
                putString("cacheAddressKey", "destinyAddress")
            }
            findNavController().navigate(R.id.nav_google_map_input, bundle)
        }


        val continueButton = view.findViewById<Button>(R.id.form_address_continue_button)
        continueButton.setOnClickListener {
            findNavController().navigate(R.id.nav_delivery_process)
        }
    }

    override fun onResume() {
        super.onResume()

        val originAddressTitle = CacheManager.get("originAddressTitle") ?: "Ingresa dirección de origen ..."
        val originAddress = CacheManager.get("originAddress")

        val destinyAddressTitle = CacheManager.get("destinyAddressTitle") ?: "Ingresa dirección de destino ..."
        val destinyAddress = CacheManager.get("destinyAddress")


        binding.formAddressOriginPlaceholder.text = originAddressTitle as String
        binding.formAddressDestinyPlaceholder.text = destinyAddressTitle as String

        if (originAddress != null && destinyAddress != null) {
            //Calcula la distancia y precio de envío
            val distancia = Utils.haversine(
                (originAddress as Address).latitude, originAddress.longitude,
                (destinyAddress as Address).latitude, destinyAddress.longitude
            )
            binding.formAddressRoadInformationDistanceText.text = "Distancia: ${Utils.formatNumber(distancia, 0)} KM"

            //Calcula el precio de envío
            val deliveryCost = Utils.calculateDeliveryCost(distancia, 36000)
            binding.formAddressRoadInformationDeliveryCostText.text = "Valor: $${Utils.formatNumber(deliveryCost, 0)} CLP"

        }

    }

}