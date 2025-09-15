package com.example.envioproductos.ui.google_map_input

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.ui.address_definition.address.Address
import android.widget.Button
import com.example.envioproductos.databinding.FragmentGoogleMapInputBinding

/**
 * Fragmento que muestra un mapa de Google para que el usuario seleccione una dirección.
 * Una vez seleccionada, la dirección se guarda en caché y el usuario puede continuar.
 */
class GoogleMapInputFragment : Fragment(R.layout.fragment_google_map_input), OnMapReadyCallback {

    // ViewBinding para acceder a la vista de forma segura
    private var _binding: FragmentGoogleMapInputBinding? = null
    private val binding get() = _binding!!

    // Instancia del mapa de Google
    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializamos el binding
        _binding = FragmentGoogleMapInputBinding.bind(view)

        // Obtenemos la referencia del fragmento de mapa dentro del layout
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map_input) as SupportMapFragment
        // Registramos el callback para cuando el mapa esté listo
        mapFragment.getMapAsync(this)

        // Botón para continuar hacia el formulario de dirección
        val continueButton = view.findViewById<Button>(R.id.google_map_input_continue_button)
        continueButton.setOnClickListener {
            // Navega al formulario de dirección cuando el usuario ya haya seleccionado una ubicación
            findNavController().navigate(R.id.nav_address_form)
        }
    }

    /**
     * Se ejecuta cuando el mapa de Google está listo para ser utilizado.
     */
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Habilitar controles de zoom en el mapa
        googleMap.uiSettings.isZoomControlsEnabled = true
        // Posicionar la cámara en un punto inicial (ejemplo: Santiago, Chile)
        val santiago = LatLng(-33.4489, -70.6693)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 12f))
        // Clave con la que se guardará la dirección en la caché
        val cacheOriginAdressKey = arguments?.getString("cacheAddressKey") ?: "originAddress"
        // Referencia al botón continuar
        val continueButton = binding.googleMapInputContinueButton
        // Configurar listener para detectar clics en el mapa
        googleMap.setOnMapClickListener { latLng ->
            // Limpiar marcadores previos
            googleMap.clear()
            // Agregar un marcador en la ubicación seleccionada
            googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación seleccionada"))
            // Usar Geocoder para obtener la dirección a partir de la latitud y longitud
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // Si se obtiene una dirección válida
            if (!addressList.isNullOrEmpty()) {
                val address = addressList[0].getAddressLine(0) // Dirección en formato de texto
                // Guardar dirección en caché junto con latitud y longitud
                CacheManager.put(cacheOriginAdressKey, Address(address, latLng.latitude, latLng.longitude))
                CacheManager.put(cacheOriginAdressKey + "Title", address)
                // Habilitar el botón de continuar
                continueButton.isEnabled = true
            }
        }
    }
}
