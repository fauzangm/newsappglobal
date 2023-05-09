package com.eduside.alfagift.data.remote

import android.os.Parcelable
import com.eduside.alfagift.utils.UNKNOWN_NETWORK_ERROR
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Error(
    @SerializedName("status") val message: String?
) : Parcelable {

    companion object {
        fun getErrorMessage(jsonString: String): String {
            val error = Gson().fromJson(jsonString, Error::class.java)
            return when {
                !error.message.isNullOrBlank() -> error.message
                else -> UNKNOWN_NETWORK_ERROR
            }
        }
    }
}