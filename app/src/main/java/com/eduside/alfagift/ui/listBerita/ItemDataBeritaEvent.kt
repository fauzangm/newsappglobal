package com.eduside.alfagift.ui.listBerita

import com.eduside.alfagift.data.local.db.entities.BeritaVo


class ItemDataBeritaEvent(var data: BeritaVo) {
    fun itemKategoriClicked(data: BeritaVo) {
        this.data = data
    }
}