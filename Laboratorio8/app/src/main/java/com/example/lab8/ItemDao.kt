package com.example.lab8

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteAllItems()

    @Query("SELECT * FROM item_table ORDER BY id DESC")
    fun getAllItems() : LiveData<List<Item>>
}