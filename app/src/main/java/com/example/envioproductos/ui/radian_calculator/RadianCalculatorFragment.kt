package com.example.envioproductos.ui.radian_calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.ui.products.product_item.ProductItem
import com.example.envioproductos.ui.products.product_item.ProductItemAdapter
import com.example.envioproductos.ui.products_cart.products_cart_item.ProductCartItem
import com.example.envioproductos.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RadianCalculatorFragment : Fragment(R.layout.fragment_radian_calculator) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calculateButton = view.findViewById<Button>(R.id.radian_calculator_button)

        calculateButton.setOnClickListener {
            val entrieNumber = view.findViewById<EditText>(R.id.radian_calculator_input).text.toString()
            if(entrieNumber.isNotEmpty()) {
                val resultNumber = view.findViewById<TextView>(R.id.radian_calculator_result)
                resultNumber.text = Utils.gradosARadianes(entrieNumber.toDouble()).toString()

            } else {
                Toast.makeText(requireContext(), "¡El valor ingresado es inválido!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}