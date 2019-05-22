package com.example.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class InventarioViewModel(application: Application) : AndroidViewModel(application){
    private val repository: InventarioRepository = InventarioRepository(application)
    private val allInventarios: LiveData<List<Inventario>> = repository.getAllInventarios()

    fun insert(inventario: Inventario){
        repository.insert(inventario)
    }

    fun update(inventario: Inventario){
        repository.update(inventario)
    }

    fun delete(inventario: Inventario){
        repository.delete(inventario)
    }

    fun deleteAllInventarios(){
        repository.deleteAllInventarios()
    }

    fun getAllInventarios(): LiveData<List<Inventario>> {
        return allInventarios
    }
}