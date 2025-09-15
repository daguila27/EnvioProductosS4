package com.example.envioproductos.ui.products_cart.products_cart_item

data class ProductCartItem (
    val productName: String, //Nombre del producto
    val productType: String, //Tipo de producto (Congelado o normal)
    val productQuantity: Int, //Cantidad de productos
    val totalCost: Int //Costo total (cantidad de productos * valor unitario)
)