package com.eduside.alfagift.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.eduside.alfagift.data.local.db.AppDB
import com.eduside.alfagift.data.local.db.dao.BeritaDao
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BeritaDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var appDb :AppDB
    lateinit var beritaDao: BeritaDao

    @Before
    fun setup() {
        appDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()
        beritaDao = appDb.beritaDao()

    }

    @Test
    fun addBerita() = runBlocking{
        val berita = BeritaVo("This App Makes Your Mac’s Keyboard Sound Vintage","Jake Peterson","Lifehacker.com","Typing on a modern Apple keyboard isn’t a silent experience by any means, but it certainly doesn’t compete with a mechanical keyboard. There’s something satisfying about the clicks and clacks produced when typing on one of those specialty keyboards",
            "https://lifehacker.com/this-app-makes-your-mac-s-keyboard-sound-vintage-1850402370","https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/42e0909b8305d91a881b8ae4179bc6d5.png",
            "Typing on a modern Apple keyboard isnt a silent experience by any means, but it certainly doesnt compete with a mechanical keyboard. Theres something satisfying about the clicks and clacks produce")
        beritaDao.addBerita(berita)
        val result = beritaDao.getBerita()
        Assert.assertEquals(1,result.size)
        Assert.assertEquals("This App Makes Your Mac’s Keyboard Sound Vintage",result[0].title)
    }

    @Test
    fun deleteBerita() = runBlocking{
        val berita = BeritaVo("This App Makes Your Mac’s Keyboard Sound Vintage","Jake Peterson","Lifehacker.com","Typing on a modern Apple keyboard isn’t a silent experience by any means, but it certainly doesn’t compete with a mechanical keyboard. There’s something satisfying about the clicks and clacks produced when typing on one of those specialty keyboards",
            "https://lifehacker.com/this-app-makes-your-mac-s-keyboard-sound-vintage-1850402370","https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/42e0909b8305d91a881b8ae4179bc6d5.png",
            "Typing on a modern Apple keyboard isnt a silent experience by any means, but it certainly doesnt compete with a mechanical keyboard. Theres something satisfying about the clicks and clacks produce")
        beritaDao.addBerita(berita)
        beritaDao.deletBerita(berita)
        val result = beritaDao.getBerita()
        Assert.assertEquals(0,result.size)
    }

    @After
    fun tearDown(){
        appDb.close()
    }
}