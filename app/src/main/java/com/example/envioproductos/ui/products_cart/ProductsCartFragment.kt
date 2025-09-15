package com.example.envioproductos.ui.products_cart

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.ui.products_cart.products_cart_item.ProductCartItem
import com.example.envioproductos.ui.products_cart.products_cart_item.ProductCartItemAdapter
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.utils.Utils

/**
 * Fragmento que muestra los productos agregados al carrito.
 */
class ProductsCartFragment : Fragment(R.layout.fragment_products_cart) {
    // ListView donde se desplegarán los productos en el carrito
    private lateinit var productsCartListView: ListView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Vincular el ListView con su elemento en el layout
        productsCartListView = view.findViewById(R.id.products_cart_list_view)
        // Obtener la lista de productos guardados en caché bajo la clave "products_cart"
        val productsCart = CacheManager.get("products_cart")
        // Crear el adaptador con el contexto actual y la lista de productos en carrito
        val adapter = ProductCartItemAdapter(
            requireContext(),
            //Es necesario para asegurar que el listado de datos entregado a Adapter
            // sea del formato esperado.
            productsCart as List<ProductCartItem>
        )
        // Asignar el adaptador al ListView para mostrar los productos
        productsCartListView.adapter = adapter
        // Referencia al botón "Continuar"
        val continueButton = view.findViewById<Button>(R.id.products_cart_continue_button)
        // Configurar el evento clic para navegar al siguiente paso (formulario de dirección)
        continueButton.setOnClickListener {
            findNavController().navigate(R.id.nav_address_form)
        }
        // Calcular el total sumando el costo de todos los productos
        var total = 0
        for (product in productsCart) {
            total += product.totalCost
        }
        // Mostrar el total en el TextView correspondiente
        val productsCartListViewTotal = view.findViewById<TextView>(R.id.products_cart_list_view_total)
        productsCartListViewTotal.text = "Total: $${Utils.formatNumber(total, 0)} CLP"
    }
}
