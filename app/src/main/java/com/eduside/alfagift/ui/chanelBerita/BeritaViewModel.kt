package com.eduside.alfagift.ui.chanelBerita

import androidx.lifecycle.*
import com.eduside.alfagift.data.repository.berita.BeritaServerRepository
import com.eduside.alfagift.data.repository.berita.GetBeritaRepository
import com.eduside.alfagift.data.repository.berita.GetBeritaResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeritaViewModel @Inject constructor(
    private val beritaServerRepository: BeritaServerRepository
) : ViewModel() {

    private val getBerita = MutableLiveData<GetBeritaResult>()
    val getBeritaError = Transformations.switchMap(getBerita) { it.error }
    val getBeritaLoading = Transformations.switchMap(getBerita) { it.loading }
    val getBeritaResponse = Transformations.switchMap(getBerita) { it.listJenisBerita }
    fun getBerita() {
        viewModelScope.launch {
            getBerita.postValue(beritaServerRepository.getBeritaItem())
        }
    }


}