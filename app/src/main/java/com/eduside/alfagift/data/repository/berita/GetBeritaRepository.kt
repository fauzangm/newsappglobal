package com.eduside.alfagift.data.repository.berita

import androidx.lifecycle.MutableLiveData
import com.eduside.alfagift.data.local.db.dao.BeritaDao
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import com.eduside.alfagift.data.remote.ApiServices
import com.eduside.alfagift.data.remote.response.ArticlesItem
import com.eduside.alfagift.data.remote.response.GetDataBeritaResponse
import com.eduside.bappenda.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBeritaRepository @Inject constructor(
    private val apiServices: ApiServices,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val beritaDao: BeritaDao
    ) {

    private val error = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()
    private val regItem = MutableLiveData<GetDataBeritaResponse>()
    val beritaVoItem = MutableLiveData<List<BeritaVo>>()
    private var id = 0



    suspend fun getSumber() {
        withContext(ioDispatcher) {
            val result = beritaDao.getSumber()
            result.let {
                beritaVoItem.postValue(it)
            }
        }
    }



    fun getListBerita(channel: String): Flow<List<BeritaVo>> {
        return beritaDao.getListBerita(channel)
    }


    //search tittle
    fun getSearchBerita(searchQuery: String,channel: String): Flow<List<BeritaVo>> {
        return beritaDao.getBerita(searchQuery,channel)
    }

    suspend fun getBeritaItem(): GetBeritaResult {
        return withContext(ioDispatcher) {
            loading.postValue(true)
            try {
                val check = beritaDao.getBerita()
                val getResponse = apiServices.getDataBerita()
                if (check.isEmpty()) {
                    if (getResponse.isSuccessful) {
                        getResponse.body()?.let {
                            it.articles?.forEach { data ->
                                saveBerita(it.articles)
                            }
                            regItem.postValue(it)
                        }
                    } else {
                        error.postValue(getResponse.errorBody()?.string().toString())
                    }
                }

                loading.postValue(false)

            } catch (e: Exception) {
                loading.postValue(false)
                e.printStackTrace()
                error.postValue(e.localizedMessage)
            }
            return@withContext GetBeritaResult(error, loading, regItem)
        }

    }


    private suspend fun saveBerita(data: List<ArticlesItem>) {
        if (data.isNotEmpty()) {
            val beritaItem: ArrayList<BeritaVo> = arrayListOf()
            data.forEach { listItem ->
                id++
                beritaItem.add(
                    BeritaVo(
                        sumber = listItem.source?.name.toString(),
                        author = listItem.author,
                        title = listItem.title.toString(),
                        description = listItem.description,
                        url = listItem.url,
                        urlToImage = listItem.urlToImage,
                        content = listItem.content
                    )
                )

            }
            delay(200L)
            beritaDao.addBerita(beritaItem)
        }

    }

}