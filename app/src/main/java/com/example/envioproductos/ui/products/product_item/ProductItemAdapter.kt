package com.example.envioproductos.ui.products.product_item

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.envioproductos.R

class ProductItemAdapter (
    private val context: Context,
    private val productsList : List<ProductItem>
    ) : BaseAdapter() {

    override fun getCount(): Int = productsList.size

    override fun getItem(position: Int): Any = productsList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.fragment_product_item, parent, false)

        val productName = view.findViewById<TextView>(R.id.product_name)
        val productType = view.findViewById<TextView>(R.id.product_type)

        val item = productsList[position]
        productName.text = item.productName
        productType.text = item.productType

        return view
    }

}