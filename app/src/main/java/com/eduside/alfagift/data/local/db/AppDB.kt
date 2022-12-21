package com.eduside.alfagift.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eduside.alfagift.data.local.db.dao.BeritaDao
import com.eduside.alfagift.data.local.db.entities.BeritaVo

@Database(
    entities =
    [
        BeritaVo::class
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDB : RoomDatabase() {

    abstract fun beritaDao(): BeritaDao

}