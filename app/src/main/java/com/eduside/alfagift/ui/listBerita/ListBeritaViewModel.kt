package com.eduside.alfagift.ui.listBerita

import androidx.lifecycle.*
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import com.eduside.alfagift.data.repository.berita.GetBeritaRepository
import com.eduside.alfagift.data.repository.berita.GetBeritaResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListBeritaViewModel @Inject constructor(
    private val beritaRepository: GetBeritaRepository
) : ViewModel() {

    fun searchDataBerita(searchQuery: String,channel: String): LiveData<List<BeritaVo>> {
        return beritaRepository.getSearchBerita(searchQuery,channel).asLiveData()
    }

    fun getListberita(channel: String): LiveData<List<BeritaVo>> {
        return beritaRepository.getListBerita(channel).asLiveData()
    }


}