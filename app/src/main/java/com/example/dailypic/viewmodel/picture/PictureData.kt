package com.example.dailypic.viewmodel.picture

import com.example.dailypic.model.PictureServerResponseData

sealed class PictureData {
    data class Success(val serverResponseData: PictureServerResponseData) : PictureData()
    data class Error (val error: Throwable) : PictureData()
    data class Loading(val progress: Int) : PictureData()
}
