package com.example.lab8

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Producto::class], version = 2, exportSchema = false)
abstract class ProductoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    companion object {

        private var INSTANCE: ProductoDatabase? = null

        fun getInstance(context: Context): ProductoDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ProductoDatabase::class.java, "item_database")
                        .fallbackToDestructiveMigration().addCallback(roomCallback).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private class PopulateDbAsyncTask (db: ProductoDatabase) : AsyncTask<Void, Void, Void>() {
            private val productoDao: ProductoDao = db.productoDao()

            override fun doInBackground(vararg voids: Void): Void? {
                productoDao.insert(Producto("Product 1", "Codigo 1"))
                productoDao.insert(Producto("Product 2", "Codigo 2"))
                productoDao.insert(Producto("Product 3", "Codigo 3"))
                return null
            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE!!)
                    .execute()
            }
        }
    }
}