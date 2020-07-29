package com.kunalmadan.github.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunalmadan.github.network.GithubApi
import com.kunalmadan.github.network.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class GithubApiStatus { LOADING, ERROR, DONE, EMPTY }

class MainViewModel : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _userInfo = MutableLiveData<User>()

    val userInfo: LiveData<User>
        get() = _userInfo

    private val _status = MutableLiveData<GithubApiStatus>()

    private var textEntered: String = ""

    val status: LiveData<GithubApiStatus>
        get() = _status


    private val _enteredUserName = MutableLiveData<String>()

    // The external immutable LiveData for the navigation property
    val enteredUserName: LiveData<String>
        get() = _enteredUserName

    fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        textEntered = s.toString();
        _enteredUserName.value = textEntered
    }

    fun onSearchClicked(){
        if(textEntered.isEmpty()){
            _status.value = GithubApiStatus.EMPTY
            return
        }
        coroutineScope.launch {
            var data = GithubApi.retrofitService.getGithubProfile(textEntered)
            try {
                _status.value = GithubApiStatus.LOADING
                val listResult = data.await()
                _status.value = GithubApiStatus.DONE
                _userInfo.value = listResult
            } catch (e: Exception) {
                _status.value = GithubApiStatus.ERROR
                //_userInfo.value = User()
            }
        }
    }

   /* fun showRepositories() {
        _enteredUserName.value = textEntered
    }*/

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}