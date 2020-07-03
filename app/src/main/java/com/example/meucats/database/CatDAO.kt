package com.example.meucats.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CatDAO {
    @Query("SELECT * from cat_table")
    fun getCats(): LiveData<List<CatTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: CatTable)

    @Update
    fun update(word: CatTable)

    @Query("DELETE FROM cat_table WHERE link=:url ")
    fun delete(url: String)
}