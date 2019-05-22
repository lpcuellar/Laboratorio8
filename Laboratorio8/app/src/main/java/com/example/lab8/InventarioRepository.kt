package com.example.lab8

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class InventarioRepository(application: Application) {

    private val inventarioDao: InventarioDao
    private val allInventarios: LiveData<List<Inventario>>
    private val database: InventarioDatabase =
        InventarioDatabase.getInstance(application)

    init {
        inventarioDao = database.inventarioDao()
        allInventarios = inventarioDao.getAllInventarios()
    }

    fun insert(inventario: Inventario) {
        InsertItemAsyncTask(inventarioDao).execute(inventario)
    }

    fun update(inventario: Inventario) {
        UpdateItemAsyncTask(inventarioDao).execute(inventario)
    }

    fun delete(inventario: Inventario) {
        DeleteItemAsyncTask(inventarioDao).execute(inventario)
    }

    fun deleteAllInventarios() {
        DeleteAllItemsAsyncTask(inventarioDao).execute()
    }

    fun getAllInventarios(): LiveData<List<Inventario>> {
        return allInventarios
    }

    private class InsertItemAsyncTask(private val inventarioDao: InventarioDao) :
        AsyncTask<Inventario, Void, Void>() {

        override fun doInBackground(vararg invs: Inventario): Void? {
            inventarioDao.insert(invs[0])
            return null
        }
    }

    private class UpdateItemAsyncTask(private val inventarioDao: InventarioDao) :
        AsyncTask<Inventario, Void, Void>() {

        override fun doInBackground(vararg invs: Inventario): Void? {
            inventarioDao.update(invs[0])
            return null
        }
    }

    private class DeleteItemAsyncTask(private val inventarioDao: InventarioDao) :
        AsyncTask<Inventario, Void, Void>() {

        override fun doInBackground(vararg invs: Inventario): Void? {
            inventarioDao.delete(invs[0])
            return null
        }
    }

    private class DeleteAllItemsAsyncTask(private val inventarioDao: InventarioDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            inventarioDao.deleteAllInventarios()
            return null
        }
    }
}