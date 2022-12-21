package com.eduside.alfagift.ui.chanelBerita

import com.eduside.alfagift.data.local.db.entities.BeritaVo


class ItemDataChannelEvent(var data: BeritaVo) {
    fun itemKategoriClicked(data: BeritaVo) {
        this.data = data
    }
}