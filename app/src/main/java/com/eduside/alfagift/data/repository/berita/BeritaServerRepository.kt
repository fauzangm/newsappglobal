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

class BeritaServerRepository @Inject constructor(
    private val apiServices: ApiServices,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,

    ) {

    private val error = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()
    private val regItem = MutableLiveData<GetDataBeritaResponse>()
    val beritaVoItem = MutableLiveData<List<BeritaVo>>()
    private var id = 0









    suspend fun getBeritaItem(): GetBeritaResult {
        return withContext(ioDispatcher) {
            loading.postValue(true)
            try {
                val getResponse = apiServices.getDataBerita()
//                if (check.isEmpty()) {
                    if (getResponse.isSuccessful) {
                        getResponse.body()?.let {

                            regItem.postValue(it)
                        }
                    } else {
                        error.postValue(getResponse.errorBody()?.string().toString())
//                    }
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




}