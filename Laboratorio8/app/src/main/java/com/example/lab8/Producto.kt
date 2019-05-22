package com.example.lab8

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
class Producto(private var nombre: String, private var codigo: String){

    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0


    fun setId(id: Int){
        this.id = id
    }

    fun setNombre(nombre: String){
        this.nombre = nombre
    }

    fun setCant(codigo: String){
        this.codigo = codigo
    }

    fun getId() : Int{
        return id
    }

    fun getNombre() : String{
        return nombre
    }

    fun getCodigo() : String{
        return codigo
    }

}
