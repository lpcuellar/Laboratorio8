package com.example.lab8

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ItemDatabase::class.java, "item_database")
                        .fallbackToDestructiveMigration().addCallback(roomCallback).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private class PopulateDbAsyncTask (db: ItemDatabase) : AsyncTask<Void, Void, Void>() {
            private val itemDao: ItemDao = db.itemDao()

            override fun doInBackground(vararg voids: Void): Void? {
                itemDao.insert(Item("Product 1", 1))
                itemDao.insert(Item("Product 2", 2))
                itemDao.insert(Item("Product 3", 3))
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