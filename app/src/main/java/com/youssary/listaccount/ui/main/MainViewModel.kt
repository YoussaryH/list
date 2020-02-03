package com.youssary.listaccount.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.server.AccountDbResult
import com.youssary.listaccount.model.repository.ListRepository
import com.youssary.listaccount.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val mListRepository: ListRepository) : ViewModel(),
    Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class DataMax(val list: MutableList<ListDB>) : UiModel()
        class Content(val list: List<ListDB>) : UiModel()
        class Navigation(val movie: AccountDbResult) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(mListRepository.findListRoom().toMutableList())
            _model.value = UiModel.DataMax(mListRepository.findListRoomMax().toMutableList())
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

