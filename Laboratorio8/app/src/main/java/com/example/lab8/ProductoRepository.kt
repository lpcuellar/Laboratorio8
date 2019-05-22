package com.example.lab8

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ProductoRepository(application: Application) {
    private val productoDao: ProductoDao
    private val allProductos: LiveData<List<Producto>>
    private val database: ProductoDatabase =
        ProductoDatabase.getInstance(application)

    init {
        productoDao = database.productoDao()
        allProductos = productoDao.getAllProductos()
    }

    fun insert(producto: Producto) {
        InsertItemAsyncTask(productoDao).execute(producto)
    }

    fun update(producto: Producto) {
        UpdateItemAsyncTask(productoDao).execute(producto)
    }

    fun delete(producto: Producto) {
        DeleteItemAsyncTask(productoDao).execute(producto)
    }

    fun deleteAllItems() {
        DeleteAllItemsAsyncTask(productoDao).execute()
    }

    fun getAllItems(): LiveData<List<Producto>> {
        return allProductos
    }

    private class InsertItemAsyncTask(private val productoDao: ProductoDao) :
        AsyncTask<Producto, Void, Void>() {

        override fun doInBackground(vararg prods: Producto): Void? {
            productoDao.insert(prods[0])
            return null
        }
    }

    private class UpdateItemAsyncTask(private val productoDao: ProductoDao) :
        AsyncTask<Producto, Void, Void>() {

        override fun doInBackground(vararg prods: Producto): Void? {
            productoDao.update(prods[0])
            return null
        }
    }

    private class DeleteItemAsyncTask(private val productoDao: ProductoDao) :
        AsyncTask<Producto, Void, Void>() {

        override fun doInBackground(vararg prods: Producto): Void? {
            productoDao.delete(prods[0])
            return null
        }
    }

    private class DeleteAllItemsAsyncTask(private val productoDao: ProductoDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            productoDao.deleteAllProductos()
            return null
        }
    }
}