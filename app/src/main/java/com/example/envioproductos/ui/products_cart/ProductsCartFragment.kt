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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.MutableList

class ProductsCartFragment : Fragment(R.layout.fragment_products_cart) {

    private lateinit var productsCartListView: ListView
    //private val productsCart: MutableList<ProductItem> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincular el ListView con el layout
        productsCartListView = view.findViewById(R.id.products_cart_list_view)

        val productsCart = CacheManager.get("products_cart")

        val adapter = ProductCartItemAdapter(requireContext(),
            productsCart as List<ProductCartItem>
        )

        productsCartListView.adapter = adapter


        val continueButton = view.findViewById<Button>(R.id.products_cart_continue_button)

        continueButton.setOnClickListener {
            findNavController().navigate(R.id.nav_address_form)
        }

        var total = 0
        for (product in productsCart) {
            total += product.totalCost
        }
        val productsCartListViewTotal = view.findViewById<TextView>(R.id.products_cart_list_view_total)
        productsCartListViewTotal.text = "Total: $${Utils.formatNumber(total, 0)} CLP"
    }
}