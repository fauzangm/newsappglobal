package com.eduside.alfagift.data.local.db.dao

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


    //getAll
    @Query("SELECT * FROM list_berita ORDER BY title ASC")
    suspend fun getBerita(): List<BeritaVo>

    //getFromSUmber
    @Query("SELECT * FROM list_berita WHERE sumber LIKE :channel")
    fun getListBerita(channel: String): Flow<List<BeritaVo>>


    //getsumber
    @Query("SELECT * FROM list_berita GROUP BY sumber")
    suspend fun getSumber(): List<BeritaVo>

    //getFromTittle
    @Query("SELECT * FROM list_berita WHERE title LIKE :searchQuery AND sumber LIKE :channel")
    fun getBerita(searchQuery: String,channel: String): Flow<List<BeritaVo>>

}