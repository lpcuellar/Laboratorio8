package com.example.lab8

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductHolder>(){
    private var products: List<Producto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentProducto = products[position]
        holder.nombre_producto.text = currentProducto.getNombre()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(prods: List<Producto>) {
        this.products = prods
        notifyDataSetChanged()
    }

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre_producto: TextView = itemView.findViewById(R.id.text_view_title)
    }

    fun getProductAt(position: Int): Producto {
        return products[position]
    }
}