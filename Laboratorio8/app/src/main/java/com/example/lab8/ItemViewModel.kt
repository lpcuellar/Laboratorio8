package com.example.lab8

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository = ItemRepository(application)
    private val allItems: LiveData<List<Item>> = repository.getAllItems()

    fun insert(item: Item){
        repository.insert(item)
    }

    fun update(item: Item){
        repository.update(item)
    }

    fun delete(item: Item){
        repository.delete(item)
    }

    fun deleteAllItems(){
        repository.deleteAllItems()
    }

    fun getAllItems(): LiveData<List<Item>>{
        return allItems
    }
}