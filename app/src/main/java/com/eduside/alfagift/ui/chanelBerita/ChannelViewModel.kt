package com.eduside.alfagift.ui.chanelBerita

import androidx.lifecycle.*
import com.eduside.alfagift.data.repository.berita.BeritaServerRepository
import com.eduside.alfagift.data.repository.berita.GetBeritaRepository
import com.eduside.alfagift.data.repository.berita.GetBeritaResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val beritaRepository: GetBeritaRepository,
) : ViewModel() {

    private val getBerita = MutableLiveData<GetBeritaResult>()
    val getBeritaError = Transformations.switchMap(getBerita) { it.error }
    val getBeritaLoading = Transformations.switchMap(getBerita) { it.loading }
    val getBeritaResponse = Transformations.switchMap(getBerita) { it.listJenisBerita }
    fun getBerita() {
        viewModelScope.launch {
            getBerita.postValue(beritaRepository.getBeritaItem())
        }
    }

    val readSumber = beritaRepository.beritaVoItem
    fun getSumber() {
        viewModelScope.launch {
            beritaRepository.getSumber()
        }
    }

}