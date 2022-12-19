package com.eduside.alfagift.di

import android.content.Context
import androidx.room.Room
import com.eduside.alfagift.data.local.db.AppDB
import com.eduside.alfagift.data.local.db.dao.BeritaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModules {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDB {
        return Room
            .databaseBuilder(context, AppDB::class.java, "seleksiAlfaGift.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDataPeople(db: AppDB): BeritaDao {
        return db.beritaDao()
    }

}