package com.example.lab8

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
class Item(private var producto: String, private var cantidad: Int) {

    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun getId(): Int{
        return id
    }

    fun getProducto(): String {
        return producto
    }

    fun getCantidad(): Int {
        return cantidad
    }

    fun setId(id: Int){
        this.id = id
    }

    fun setProducto(producto: String){
        this.producto = producto
    }

    fun setCantidad(cantidad: Int){
        this.cantidad = cantidad
    }
}