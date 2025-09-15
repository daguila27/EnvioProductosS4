package com.example.envioproductos.ui.products_cart.products_cart_item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.envioproductos.R
import com.example.envioproductos.utils.Utils

// Adaptador personalizado para mostrar los productos que están en el carrito
class ProductCartItemAdapter (
    // Contexto de la aplicación o fragmento donde se mostrará la lista
    private val context: Context,
    // Lista de productos en el carrito de comprar
    private val productsCartList : List<ProductCartItem>
) : BaseAdapter() {
    // Retorna la cantidad de elementos en la lista
    override fun getCount(): Int = productsCartList.size
    // Retorna el objeto en una posición específica
    override fun getItem(position: Int): Any = productsCartList[position]
    // Retorna un identificador único para cada elemento (aquí usamos la posición)
    override fun getItemId(position: Int): Long = position.toLong()
    // Retorna la vista correspondiente a cada elemento de la lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Si ya existe una vista reciclada (convertView), la usamos, si no, inflamos una nueva
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_products_cart_item, parent, false)

        // Obtenemos las referencias a los elementos visuales del layout fragment_products_cart_item
        val productCartName = view.findViewById<TextView>(R.id.product_cart_name)       // Nombre del producto
        val productCartType = view.findViewById<TextView>(R.id.product_cart_type)       // Tipo del producto
        val productCartQuantity = view.findViewById<TextView>(R.id.product_cart_quantity) // Costo total del producto en el carrito

        // Obtenemos el producto correspondiente a la posición actual
        val item = productsCartList[position]

        // Asignamos valores del objeto a los elementos en archivo .xml
        productCartName.text = item.productName
        productCartType.text = item.productType
        // Se da formato al total de la compra
        productCartQuantity.text = "$${Utils.formatNumber(item.totalCost, 0)} (CLP)"

        // Retornamos la vista
        return view
    }
}
