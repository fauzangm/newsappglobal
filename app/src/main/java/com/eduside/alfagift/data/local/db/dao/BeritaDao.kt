package com.eduside.alfagift.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import kotlinx.coroutines.flow.Flow

@Dao
interface BeritaDao {

    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBerita(item: BeritaVo)
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBerita(item: List<BeritaVo>)

    //delete
    @Delete
    suspend fun deletBerita(item: BeritaVo)


    //get
    @Query("SELECT * FROM list_berita ORDER BY name ASC")
    suspend fun getBerita(): List<BeritaVo>

    //getFromID
    @Query("SELECT * FROM list_berita WHERE name LIKE :searchQuery")
    fun getBerita(searchQuery: String): Flow<List<BeritaVo>>

}