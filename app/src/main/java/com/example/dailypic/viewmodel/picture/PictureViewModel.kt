package com.example.dailypic.viewmodel.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailypic.model.PictureRetrofitImpl
import com.example.dailypic.model.PictureServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val apiKey: String = "OJUh9f1hibYtGedcgk2OUFSIQbY1Rb99D3CTiSf3"

class PictureViewModel (
    private val liveData: MutableLiveData<PictureData> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofitImpl = PictureRetrofitImpl()
    ) :
    ViewModel() {

        fun getData(): LiveData<PictureData> {
            sendServerRequest()
            return liveData
        }

        private fun sendServerRequest() {
            liveData.value = PictureData.Loading(null)
            if (apiKey.isBlank()) {
                PictureData.Error(Throwable("You need API key"))
            } else {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                    Callback<PictureServerResponseData> {
                    override fun onResponse(
                        call: Call<PictureServerResponseData>,
                        response: Response<PictureServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value =
                                PictureData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveData.value =
                                    PictureData.Error(Throwable("Unidentified error"))
                            } else {
                                liveData.value =
                                    PictureData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PictureServerResponseData>, t: Throwable) {
                        liveData.value = PictureData.Error(t)
                    }
                })
            }
        }
}