package com.maoungedev.shaquillecafeapp.ui.presentation.kitchen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.usecase.kitchen.KitchenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitchenViewModel @Inject constructor(
    private val kitchenUseCase: KitchenUseCase
): ViewModel() {

    private val state = MutableStateFlow<KitchenUiState>(KitchenUiState.Init)
    val mState: StateFlow<KitchenUiState> get() = state

    private val kitchenQueue = MutableStateFlow<List<OrderMenu>>(mutableListOf())
    val mKitchenQueue: StateFlow<List<OrderMenu>> get() = kitchenQueue

    private val message = MutableSharedFlow<String>()
    val mMessage: SharedFlow<String> get() = message

    private fun showLoading() {
        state.value = KitchenUiState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = KitchenUiState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = KitchenUiState.ShowToast(message)
    }

    private fun emitMessage(msg: String) {
        viewModelScope.launch {
            message.emit(msg)
        }
    }

    fun fetchKitchenQueue() {
        viewModelScope.launch {
            kitchenUseCase.getAllKitchenQueue()
                .onStart {
                    showLoading()
                }
                .catch { e->
                    hideLoading()
                    showToast(e.message.toString())
                }
                .collect {result ->
                    hideLoading()
                    when(result) {
                        is BaseResult.Error -> showToast(result.message)
                        is BaseResult.Success -> kitchenQueue.value = result.data
                    }
                }
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            kitchenUseCase.deleteOrder(orderId)
                .catch { e ->
                    emitMessage(e.message.toString())
                }
                .collect {
                    emitMessage(it)
                }
        }
    }
}

sealed class KitchenUiState {
    object Init: KitchenUiState()
    data class IsLoading(val isLoading: Boolean): KitchenUiState()
    data class ShowToast(val message: String): KitchenUiState()
}