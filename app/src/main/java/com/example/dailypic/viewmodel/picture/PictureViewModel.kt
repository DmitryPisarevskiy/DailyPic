package com.example.dailypic.viewmodel.picture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PictureViewModel : ViewModel() {
    private val liveData: MutableLiveData<PictureData> = MutableLiveData()
}