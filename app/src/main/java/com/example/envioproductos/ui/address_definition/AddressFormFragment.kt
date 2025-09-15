package com.example.envioproductos.ui.address_definition
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.databinding.FragmentAddressFormBinding
import com.example.envioproductos.ui.address_definition.address.Address
import com.example.envioproductos.utils.Utils
/**
 * Fragmento encargado de definir las direcciones de origen y destino
 * para el proceso de envío de productos.
 */
class AddressFormFragment : Fragment(R.layout.fragment_address_form)  {

    // ViewBinding para acceder a los elementos del layout
    private var _binding: FragmentAddressFormBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializamos el binding con la vista inflada
        _binding = FragmentAddressFormBinding.bind(view)
        // 👉 Al hacer clic en el campo de dirección de ORIGEN
        val originInputLayout = view.findViewById<LinearLayout>(R.id.form_address_origin_input)
        originInputLayout.setOnClickListener {
            // Guardamos en el bundle qué dirección se está editando
            val bundle = Bundle().apply { putString("cacheAddressKey", "originAddress") }
            // Navegamos al fragmento de Google Maps para seleccionar dirección
            findNavController().navigate(R.id.nav_google_map_input, bundle)
        }
        // Al hacer clic en el campo de dirección de DESTINO
        val destinyInputLayout = view.findViewById<LinearLayout>(R.id.form_address_destiny_input)
        destinyInputLayout.setOnClickListener {
            val bundle = Bundle().apply { putString("cacheAddressKey", "destinyAddress") }
            // Navegamos al fragmento de Google Maps para seleccionar dirección
            findNavController().navigate(R.id.nav_google_map_input, bundle)
        }
        // Botón para continuar al proceso de entrega
        val continueButton = view.findViewById<Button>(R.id.form_address_continue_button)
        continueButton.setOnClickListener {
            // Navega al siguiente paso del flujo: proceso de entrega
            findNavController().navigate(R.id.nav_delivery_process)
        }
    }

    override fun onResume() {
        super.onResume()
        // Recuperamos títulos y direcciones desde el CacheManager
        val originAddressTitle = CacheManager.get("originAddressTitle") ?: "Ingresa dirección de origen ..."
        val originAddress = CacheManager.get("originAddress")
        val destinyAddressTitle = CacheManager.get("destinyAddressTitle") ?: "Ingresa dirección de destino ..."
        val destinyAddress = CacheManager.get("destinyAddress")
        // Actualizamos los placeholders en pantalla con las direcciones seleccionadas
        binding.formAddressOriginPlaceholder.text = originAddressTitle as String
        binding.formAddressDestinyPlaceholder.text = destinyAddressTitle as String
        // Si ambas direcciones existen, calculamos distancia y costo
        if (originAddress != null && destinyAddress != null) {
            // Calcula la distancia usando la fórmula de Haversine (en KM)
            val distancia = Utils.haversine(
                (originAddress as Address).latitude, originAddress.longitude,
                (destinyAddress as Address).latitude, destinyAddress.longitude
            )
            binding.formAddressRoadInformationDistanceText.text = "Distancia: ${Utils.formatNumber(distancia, 0)} KM"
            // Calcula el precio de envío en base a la distancia y un costo base
            val deliveryCost = Utils.calculateDeliveryCost(distancia, 36000)
            binding.formAddressRoadInformationDeliveryCostText.text = "Valor: $${Utils.formatNumber(deliveryCost, 0)} CLP"
        }
    }
}
