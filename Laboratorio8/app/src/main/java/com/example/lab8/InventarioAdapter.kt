package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InventarioAdapter: RecyclerView.Adapter<InventarioAdapter.InventarioHolder>() {
    private var inventarios: List<Inventario> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventarioHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return InventarioHolder(itemView)
    }

    override fun onBindViewHolder(holder: InventarioHolder, position: Int) {
        val currentInventario = inventarios[position]
        holder.nombre_producto.text = currentInventario.toString()
    }

    override fun getItemCount(): Int {
        return inventarios.size
    }

    fun setInventarios(prods: List<Inventario>) {
        this.inventarios = prods
        notifyDataSetChanged()
    }

    class InventarioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre_producto: TextView = itemView.findViewById(R.id.text_view_title)
    }

    fun getProductAt(position: Int): Inventario {
        return inventarios[position]
    }
}