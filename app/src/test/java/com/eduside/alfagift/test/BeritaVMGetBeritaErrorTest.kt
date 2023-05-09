package com.eduside.alfagift.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.eduside.alfagift.base.BaseTest
import com.eduside.alfagift.base.getOrAwaitValue
import com.eduside.alfagift.data.local.db.AppDB
import com.eduside.alfagift.data.local.db.dao.BeritaDao
import com.eduside.alfagift.data.remote.ApiServices
import com.eduside.alfagift.data.repository.berita.BeritaServerRepository
import com.eduside.alfagift.ui.chanelBerita.BeritaViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BeritaVMGetBeritaErrorTest : BaseTest() {

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    lateinit var appDb : AppDB
    lateinit var beritaDao: BeritaDao
    lateinit var viewModel: BeritaViewModel
    lateinit var beritaRepository: BeritaServerRepository
    lateinit var apiServices: ApiServices
    lateinit var server: MockWebServer

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        //setup ROOM
        appDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).allowMainThreadQueries().build()
        beritaDao = appDb.beritaDao()
        // setup MockWebServer
        server = this.mockWebServer
        val baseUrlApi = server.url("/error/")

        apiServices = getClient(baseUrlApi)
        beritaRepository = BeritaServerRepository(apiServices, testDispatcher,beritaDao)
        viewModel = BeritaViewModel(beritaRepository)
    }

    @After
    override fun teardown() {
        super.teardown()
        Dispatchers.resetMain()
    }

    @Test
    fun `Ketika request getBerita(), response gagal`() {
        runBlocking {
            viewModel.getSyncBerita()
            val request = server.takeRequest()
            Truth.assertThat(request.requestLine).isEqualTo("GET /error/everything?q=Apple&from=2023-04-10&sortBy=popularity HTTP/1.1")
            Truth.assertThat(viewModel.getBeritaError.getOrAwaitValue())
                .isEqualTo("error")


        }
    }

    override fun isMockServerEnabled(): Boolean = true
}