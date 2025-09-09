package com.example.envioproductos.ui.google_map_input

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.envioproductos.databinding.FragmentDeliveryProcessBinding
import com.example.envioproductos.databinding.FragmentGoogleMapInputBinding

class GoogleMapInputFragment : Fragment(R.layout.fragment_google_map_input), OnMapReadyCallback {
    private var _binding: FragmentGoogleMapInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGoogleMapInputBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map_input) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val continueButton = view.findViewById<Button>(R.id.google_map_input_continue_button)
        continueButton.setOnClickListener {
            findNavController().navigate(R.id.nav_address_form)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Habilitar zoom y gestos
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Mover la cámara a un punto inicial (ej: Santiago, Chile)
        val santiago = LatLng(-33.4489, -70.6693)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 12f))


        //Key del dato en cache donde vamos a almacenar la dirección seleccionada
        val cacheOriginAdressKey = arguments?.getString("cacheAddressKey") ?: "originAddress"

        val continueButton = binding.googleMapInputContinueButton

        // Escuchar cuando el usuario toca el mapa
        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear() // limpiar marcadores anteriores
            googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación seleccionada"))

            // Aquí puedes obtener la dirección con Geocoder
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (!addressList.isNullOrEmpty()) {
                val address = addressList[0].getAddressLine(0)
                CacheManager.put(cacheOriginAdressKey, Address(address, latLng.latitude, latLng.longitude))
                CacheManager.put(cacheOriginAdressKey+"Title", address)
                continueButton.isEnabled = true
            }
        }
    }
}

