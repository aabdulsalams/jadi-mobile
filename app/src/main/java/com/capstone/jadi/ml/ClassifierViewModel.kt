package com.capstone.jadi.ml

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.data.remote.history.GetHistoriesResponse
import com.capstone.jadi.data.remote.history.AddHistoriesResponse
import com.capstone.jadi.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ClassifierViewModel @Inject constructor(private val historyRepository: HistoryRepository): ViewModel() {

    fun getAllHistories(token: String) : LiveData<ApiResponse<GetHistoriesResponse>> {
        val result = MutableLiveData<ApiResponse<GetHistoriesResponse>>()
        viewModelScope.launch {
            historyRepository.getAllHistories(token).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun addNewHistory(token: String, name: RequestBody, file: MultipartBody.Part): LiveData<ApiResponse<AddHistoriesResponse>> {
        val result = MutableLiveData<ApiResponse<AddHistoriesResponse>>()
        viewModelScope.launch {
            historyRepository.addNewHistory(token, name, file).collect {
                result.postValue(it)
            }
        }
        return result
    }

}
