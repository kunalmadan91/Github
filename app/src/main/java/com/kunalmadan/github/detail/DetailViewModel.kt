package com.kunalmadan.github.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunalmadan.github.network.GithubApi
import com.kunalmadan.github.network.Repository
import kotlinx.coroutines.*

enum class RepositoryApiStatus { LOADING, ERROR, DONE }

class DetailViewModel(userName: String, app: Application) : AndroidViewModel(app) {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<RepositoryApiStatus>()

    val status: LiveData<RepositoryApiStatus>
        get() = _status

    private val _repositoryInfo = MutableLiveData<List<Repository>>()

    val repositoryInfo: LiveData<List<Repository>>
        get() = _repositoryInfo

    private var userName = ""
    init {
        this.userName = userName
        fetchRepositories()
    }

    fun fetchRepositories(){
        coroutineScope.launch {
            var data = GithubApi.retrofitService.getGithubRepos(userName)
            try {
                _status.value = RepositoryApiStatus.LOADING
                val listResult = data.await()
                _status.value = RepositoryApiStatus.DONE
                _repositoryInfo.value = listResult
            } catch (e: Exception) {
                _status.value = RepositoryApiStatus.ERROR
                //_userInfo.value = User()
            }
        }
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

