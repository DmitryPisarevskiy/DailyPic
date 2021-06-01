package com.example.dailypic.viewmodel.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailypic.model.MarsServerResponseData
import com.example.dailypic.model.PictureRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel (
    private val liveData: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofitImpl = PictureRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<MarsData> {
        sendServerRequest()
        return liveData
    }

    private fun sendServerRequest() {
        liveData.value = MarsData.Loading(null)
        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getMarsPhotos("2020-5-30",apiKey).enqueue(object :
                Callback<MarsServerResponseData> {
                override fun onResponse(
                    call: Call<MarsServerResponseData>,
                    response: Response<MarsServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveData.value =
                            MarsData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveData.value =
                                MarsData.Error(Throwable("Unidentified error"))
                        } else {
                            liveData.value =
                                MarsData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
                    liveData.value = MarsData.Error(t)
                }
            })
        }
    }
}