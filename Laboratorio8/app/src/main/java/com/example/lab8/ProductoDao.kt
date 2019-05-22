package com.example.lab8

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductoDao {

    @Insert
    fun insert(producto: Producto)

    @Update
    fun update(producto: Producto)

    @Delete
    fun delete(producto: Producto)

    @Query("DELETE FROM product_table")
    fun deleteAllProductos()

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProductos() : LiveData<List<Producto>>
}