package com.maoungedev.shaquillecafeapp.ui.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maoungedev.shaquillecafeapp.domain.common.BaseResult
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.domain.usecase.order.OrderMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCase: OrderMenuUseCase
) : ViewModel() {

    private var tableNumber: String? = null

    var totalPrice: Int = 0

    private val state = MutableStateFlow<OrderUiState>(OrderUiState.Init)
    val mState: StateFlow<OrderUiState> get() = state

    private val message = MutableSharedFlow<String>()
    val mMessage: SharedFlow<String> get() = message

    private val caffeMenu = MutableStateFlow<List<CaffeMenu>>(mutableListOf())
    val mCaffeMenu: StateFlow<List<CaffeMenu>> get() = caffeMenu

    private val ordersMenu = MutableStateFlow<List<OrderMenu>>(mutableListOf())
    val mOrdersMenu: StateFlow<List<OrderMenu>> get() = ordersMenu

    private fun showLoading() {
        state.value = OrderUiState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = OrderUiState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = OrderUiState.ShowToast(message)
    }

    private fun emitMessage(msg: String) {
        viewModelScope.launch {
            message.emit(msg)
        }
    }

     private fun calculateTotalPrice(data: List<OrderMenu>){
        totalPrice = data.sumOf {
            it.price.toInt()
        }
    }

    fun setTableNumber(number: String) {
        tableNumber = number
    }

    fun fetchAllCaffeMenu() {
        viewModelScope.launch {
            orderUseCase.getAllMenu()
                .onStart {
                    showLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> showToast(result.message)
                        is BaseResult.Success -> caffeMenu.value = result.data
                    }
                }
        }
    }

    fun fetchOrders() {
        viewModelScope.launch {
            orderUseCase.getOrders()
                .onStart {
                    showLoading()
                }
                .catch { e ->
                    hideLoading()
                    //showToast(e.message.toString())
                }
                .collectLatest { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Error -> showToast(result.message)
                        is BaseResult.Success -> {
                            calculateTotalPrice(result.data)
                            ordersMenu.value = result.data
                        }
                    }
                }
        }
    }

    fun addOrder(menuId: String) {
        viewModelScope.launch {
            orderUseCase.addOrder(menuId, tableNumber ?: "")
                .catch { e ->
                    emitMessage(e.message.toString())
                }
                .collect {
                    emitMessage(it)
                }
        }
    }

    fun updateOrder() {
        viewModelScope.launch {
            orderUseCase.updateOrder()
                .catch { e ->
                    emitMessage(e.message.toString())
                }
                .collect {
                    emitMessage(it)
                }
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            orderUseCase.deleteOrder(orderId)
                .catch { e ->
                    emitMessage(e.message.toString())
                }
                .collect {
                    emitMessage(it)
                }
        }
    }

}

sealed class OrderUiState {
    object Init : OrderUiState()
    data class IsLoading(val isLoading: Boolean) : OrderUiState()
    data class ShowToast(val message: String) : OrderUiState()
}