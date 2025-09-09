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


class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var productsListView: ListView
    private val productsCart: MutableList<ProductCartItem> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincular el ListView con el layout
        productsListView = view.findViewById(R.id.products_list_view)

        val data = listOf(
            ProductItem("Carne", "Congelado", 4500),
            ProductItem("Arroz", "Normal", 1500),
            ProductItem("Azucar", "Normal", 12000),
            ProductItem("Salmon", "Congelado", 3200)
        )
        val adapter = ProductItemAdapter(requireContext(), data)

        productsListView.adapter = adapter


        productsListView.setOnItemClickListener { _, _, position, _ ->
            val product = data[position]
            val quantity = 1
            productsCart.add(ProductCartItem(product.productName, product.productType, quantity, product.cost*quantity))
            Toast.makeText(requireContext(), "${product.productName} añadido al carrito", Toast.LENGTH_SHORT).show()
        }


        val fab = view.findViewById<FloatingActionButton>(R.id.products_cart_float_button)

        fab.setOnClickListener {
            // Aquí defines la acción
            Toast.makeText(requireContext(), "Abrir carrito", Toast.LENGTH_SHORT).show()

            CacheManager.put("products_cart", productsCart)
            findNavController().navigate(R.id.nav_products_cart)
        }

    }
}