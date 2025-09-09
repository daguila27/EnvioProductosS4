package com.example.envioproductos.ui.products_cart.products_cart_item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.envioproductos.R

class ProductCartItemAdapter (
    private val context: Context,
    private val productsCartList : List<ProductCartItem>
) : BaseAdapter() {

    override fun getCount(): Int = productsCartList.size

    override fun getItem(position: Int): Any = productsCartList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_products_cart_item, parent, false)

        val productCartName = view.findViewById<TextView>(R.id.product_cart_name)
        val productCartType = view.findViewById<TextView>(R.id.product_cart_type)
        val productCartQuantity = view.findViewById<TextView>(R.id.product_cart_quantity)

        val item = productsCartList[position]
        productCartName.text = item.productName
        productCartType.text = item.productType
        productCartQuantity.text = item.productQuantity.toString()

        return view
    }

}