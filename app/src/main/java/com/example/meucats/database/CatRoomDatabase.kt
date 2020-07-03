package com.example.meucats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatTable::class], version = 1)
abstract class CatRoomDatabase : RoomDatabase() {
    abstract fun CatDao(): CatDAO
    companion object {
        var INSTANCE: CatRoomDatabase? = null

        fun getWordRoomDatabase(context: Context): CatRoomDatabase? {
            if (INSTANCE == null){
                synchronized(CatDAO::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, CatRoomDatabase::class.java, "word_table").build()
                }
            }
            return INSTANCE
        }
    }
}