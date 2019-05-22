package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>(){
    private var items: List<Item> = ArrayList()

    class ItemHolder(itemView:View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var nombreProducto : TextView = itemView.findViewById(R.id.nombreProducto)
        var cantidadProducto : TextView = itemView.findViewById(R.id.cantidadProducto)
        var botonMas : Button = itemView.findViewById(R.id.botonMas)
        var botonMenos : Button = itemView.findViewById(R.id.botonMenos)

        override fun onClick(v: View?) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val contentView = LayoutInflater.from(parent.context).inflate(R.layout.item, null)
        return ItemHolder(contentView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemProducto = items[position]
        holder.nombreProducto.text = itemProducto.getProducto()
        holder.cantidadProducto.text = itemProducto.getCantidad().toString()
        holder.botonMas.setOnClickListener{
            var numero = itemProducto.getCantidad() + 1
            itemProducto.setCantidad(numero)
            holder.cantidadProducto.text = itemProducto.getCantidad().toString()
        }
        holder.botonMenos.setOnClickListener{
            var numero = itemProducto.getCantidad() - 1
            if(numero >= 0) itemProducto.setCantidad(numero)
            else{
                numero = 0
                itemProducto.setCantidad(numero)
            }
            holder.cantidadProducto.text = itemProducto.getCantidad().toString()
        }
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<Item>){
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): Item {
        return items[position]
    }
}