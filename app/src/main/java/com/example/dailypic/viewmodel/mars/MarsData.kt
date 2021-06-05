package com.example.dailypic.viewmodel.picture

import com.example.dailypic.model.MarsServerResponseData

sealed class MarsData {
    data class Success(val serverResponseData: MarsServerResponseData) : MarsData()
    data class Error (val error: Throwable) : MarsData()
    data class Loading(val progress: Int?) : MarsData()
}
