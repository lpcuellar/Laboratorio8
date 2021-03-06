package com.example.lab8

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Inventario::class], version = 3, exportSchema = false)
abstract class InventarioDatabase : RoomDatabase(){

    abstract fun inventarioDao(): InventarioDao

    companion object {

        private var INSTANCE: InventarioDatabase? = null

        fun getInstance(context: Context): InventarioDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        InventarioDatabase::class.java, "item_database")
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}