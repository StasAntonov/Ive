package com.example.ive.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverSharedViewModel @Inject constructor(): ViewModel() {
    private var _userName = MutableLiveData<String>()
    val userName:LiveData<String> = _userName

    fun saveUserName(userName:String){
        _userName.value = userName
    }
}
