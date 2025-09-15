package com.example.envioproductos.ui.products

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.envioproductos.R
import com.example.envioproductos.ui.products.product_item.ProductItem
import com.example.envioproductos.ui.products.product_item.ProductItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.envioproductos.cache.CacheManager
import com.example.envioproductos.ui.products_cart.products_cart_item.ProductCartItem

// Fragment que representa la pantalla de productos
class ProductsFragment : Fragment(R.layout.fragment_products) {

    // ListView que mostrará los productos
    private lateinit var productsListView: ListView
    // Lista mutable para almacenar los productos que el usuario agregue al carrito
    private val productsCart: MutableList<ProductCartItem> = mutableListOf()

    // Se ejecuta cuando la vista es creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincular el ListView con el componente .xml fragment_products.xml
        productsListView = view.findViewById(R.id.products_list_view)

        // Lista de productos de ejemplo que se mostrarán en pantalla
        val data = listOf(
            ProductItem("Carne", "Congelado", 4500),
            ProductItem("Arroz", "Normal", 1500),
            ProductItem("Azucar", "Normal", 12000),
            ProductItem("Salmon", "Congelado", 3200)
        )

        // Adaptador personalizado que se encargará de mostrar los productos en el ListView
        val adapter = ProductItemAdapter(requireContext(), data)
        productsListView.adapter = adapter

        // Listener para manejar el clic en cada producto del ListView
        productsListView.setOnItemClickListener { _, _, position, _ ->
            // Obtenemos el producto seleccionado
            val product = data[position]
            // Por defecto, añadimos 1 producto
            val quantity = 1
            // Agregamos el producto al carrito (productsCart)
            productsCart.add(
                ProductCartItem(
                    product.productName,
                    product.productType,
                    quantity,
                    product.cost * quantity // calculamos el total según cantidad
                )
            )
            // Mostramos mensaje al usuario confirmando que se añadió al carrito
            Toast.makeText(
                requireContext(),
                "${product.productName} añadido al carrito",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Referencia al FloatingActionButton que abre el carrito
        val fab = view.findViewById<FloatingActionButton>(R.id.products_cart_float_button)

        // Listener del botón flotante
        fab.setOnClickListener {
            // Mensaje de prueba al usuario
            Toast.makeText(requireContext(), "Abriendo carrito", Toast.LENGTH_SHORT).show()

            // Guardamos los productos del carrito en caché
            CacheManager.put("products_cart", productsCart)

            // Navegamos a la pantalla del carrito usando Navigation Component
            findNavController().navigate(R.id.nav_products_cart)
        }
    }
}
