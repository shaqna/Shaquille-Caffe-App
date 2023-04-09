package com.maoungedev.shaquillecafeapp.ui.presentation.caffe

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.usecase.caffe.CaffeMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaffeViewModel @Inject constructor(private val caffeMenuUseCase: CaffeMenuUseCase) :
    ViewModel() {

    private val state = MutableStateFlow<CaffeMenuUiState>(CaffeMenuUiState.Init)
    val mState: StateFlow<CaffeMenuUiState> get() = state

    private val caffeMenu = MutableStateFlow<List<CaffeMenu>>(mutableListOf())
    val mCaffeMenu: StateFlow<List<CaffeMenu>> get() = caffeMenu

    private var category: String? = null


    private fun setLoading() {
        state.value = CaffeMenuUiState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = CaffeMenuUiState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = CaffeMenuUiState.ShowToast(message)
    }

    fun processIntent(intent: Intent) {
        category = intent.extras?.getString(CaffeMenuActivity.CATEGORY)
    }

    fun fetchCaffeMenuByCategory() {
        viewModelScope.launch {
            caffeMenuUseCase.getMenuCaffeByCategory(category ?: "")
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect {result ->
                    hideLoading()
                    when(result) {
                        is BaseResult.Error -> showToast(result.message)
                        is BaseResult.Success -> caffeMenu.value = result.data
                    }
                }
        }
    }

}

sealed class CaffeMenuUiState {
    object Init : CaffeMenuUiState()
    data class IsLoading(val isLoading: Boolean) : CaffeMenuUiState()
    data class ShowToast(val message: String) : CaffeMenuUiState()
}

