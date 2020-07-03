package com.example.meucats.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")

class CatTable (
        @PrimaryKey
        @ColumnInfo(name = "link") val link: String
)