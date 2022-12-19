package com.eduside.alfagift.data.local.db.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "list_berita")
data class BeritaVo(


    @PrimaryKey(autoGenerate = false)
    @NonNull
    var name: String = "",

    @ColumnInfo(name = "author")
    var author: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,
    @ColumnInfo(name = "content")
    var content: String? = null,
) : Parcelable