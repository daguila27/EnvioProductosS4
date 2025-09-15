package com.example.envioproductos.ui.products.product_item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.envioproductos.R
import com.example.envioproductos.utils.Utils

// Adaptador para mostrar una lista de productos (ProductItem) en un ListView
class ProductItemAdapter (
    // Contexto de la aplicación o Activity donde se mostrará el adaptador
    private val context: Context,
    // Lista de productos, de donde se va a extraer el producto a mostrar
    private val productsList : List<ProductItem>
) : BaseAdapter() {

    // Retorna la cantidad de elementos en la lista
    override fun getCount(): Int = productsList.size

    // Retorna el objeto ProductItem en una posición específica
    override fun getItem(position: Int): Any = productsList[position]

    // Retorna el ID del elemento, aquí simplemente se usa la posición convertida a Long
    override fun getItemId(position: Int): Long = position.toLong()

    // Retorna la vista que se mostrará para cada elemento en la lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Si ya existe una vista reciclada (convertView), la reutiliza; si no, infla una nueva
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_product_item, parent, false)

        // Obtenemos las referencias a los elementos visuales del layout fragment_product_item
        val productName = view.findViewById<TextView>(R.id.product_name)
        val productType = view.findViewById<TextView>(R.id.product_type)
        val productCost = view.findViewById<TextView>(R.id.product_cost)

        // Obtenemos el producto correspondiente a la posición actual
        val item = productsList[position]

        // Asignamos los valores del producto a los TextViews
        productName.text = item.productName        // Nombre del producto
        productType.text = item.productType        // Tipo del producto
        // Se da formato al precio del producto
        productCost.text = "$${Utils.formatNumber(item.cost, 0)} CLP"

        // Retorna la vista configurada para que se muestre en el ListView/GridView
        return view
    }
}
