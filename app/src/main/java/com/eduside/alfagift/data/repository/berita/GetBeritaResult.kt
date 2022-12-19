package com.eduside.alfagift.data.repository.berita

import androidx.lifecycle.LiveData
import com.eduside.alfagift.data.remote.response.GetDataBeritaResponse

data class GetBeritaResult (
    val error: LiveData<String>,
    val loading: LiveData<Boolean>,
    val listJenisBerita: LiveData<GetDataBeritaResponse>,
)