package com.youssary.listaccount.ui.module.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youssary.listaccount.R
import com.youssary.listaccount.Util
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.server.AccountDbResult
import com.youssary.listaccount.model.repository.ListRepository
import com.youssary.listaccount.ui.common.Scope
import kotlinx.android.synthetic.main.activity_main_data.*
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
        class Navigation(val mList: AccountDbResult) : UiModel()
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
            var mListDB = mListRepository.findListRoomMax().toMutableList()
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(mListRepository.findListRoom().toMutableList())
            _model.value = UiModel.DataMax(mListDB)

        }
    }

    fun onMovieClicked(list: AccountDbResult) {
        _model.value = UiModel.Navigation(list)
    }



    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

