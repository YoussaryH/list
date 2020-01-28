package com.youssary.listaccount.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.youssary.listaccount.model.AccountDbResult
import com.youssary.listaccount.model.ListRepository
import com.youssary.listaccount.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: ListRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movies: List<AccountDbResult>) : UiModel()
        class Navigation(val movie: AccountDbResult) : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(moviesRepository.getList())
        }
    }

    fun onMovieClicked(movie: AccountDbResult) {
        _model.value = UiModel.Navigation(movie)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: ListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(moviesRepository) as T
}