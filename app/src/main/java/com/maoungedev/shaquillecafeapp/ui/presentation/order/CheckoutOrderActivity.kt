package com.maoungedev.shaquillecafeapp.ui.presentation.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maoungedev.shaquillecafeapp.databinding.ActivityCheckoutOrderBinding
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.ui.presentation.home.HomeActivity
import com.maoungedev.shaquillecafeapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CheckoutOrderActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutOrderBinding by lazy {
        ActivityCheckoutOrderBinding.inflate(layoutInflater)
    }

    private val viewModel: OrderViewModel by viewModels()

    private val checkoutOrderAdapter: CheckoutOrderAdapter by lazy {
        CheckoutOrderAdapter().apply {
            setButtonCancelClickListener {orderId ->
                doCancel(orderId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        fetchOrders()
        observe()
        onActionButton()
        onRefresh()

    }

    private fun onRefresh() {
        binding.root.setOnRefreshListener {
            viewModel.fetchOrders()
            observeOrderRefresh()
        }
    }


    private fun onActionButton() {
        binding.apply {
            btnDoOrder.setOnClickListener {
                if(checkoutOrderAdapter.itemCount == 0) {
                    showToast("Pesanan Belum Ada")
                } else {
                    viewModel.updateOrder()
                    startActivity(Intent(this@CheckoutOrderActivity, HomeActivity::class.java)).also {
                        finish()
                    }
                }
            }
        }
    }

    private fun doCancel(orderId: String) {
        viewModel.deleteOrder(orderId)
        //viewModel.fetchOrders()
//        observeOrder()
    }

    private fun observe() {
        observeState()
        observeMessage()
        observeOrder()
    }

    private fun observeOrder() {
        viewModel.mOrdersMenu.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                handleOrder(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeOrderRefresh() {
        viewModel.mOrdersMenu.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                handleOrderRefresh(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeMessage() {
        viewModel.mMessage.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleMessage(it)
            }
            .launchIn(lifecycleScope)
    }



    private fun observeState() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleState(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(it: OrderUiState) {
        when(it) {
            OrderUiState.Init -> Unit
            is OrderUiState.IsLoading -> handleLoading(it.isLoading)
            is OrderUiState.ShowToast -> showToast(it.message)
        }
    }

    private fun handleOrder(it: List<OrderMenu>?) {
        if(it.isNullOrEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.tvTotalPrice.text = Helper.generateTextPrice(viewModel.totalPrice.toString())
        } else {
            binding.tvEmpty.visibility = View.GONE
            checkoutOrderAdapter.setListItem(it)
            binding.tvTotalPrice.text = Helper.generateTextPrice(viewModel.totalPrice.toString())
        }

    }

    private fun handleOrderRefresh(it: List<OrderMenu>) {
        binding.root.isRefreshing = false
        checkoutOrderAdapter.setListItem(it)
        binding.tvTotalPrice.text = Helper.generateTextPrice(viewModel.totalPrice.toString())
    }

    private fun handleMessage(it: String) {
        showToast(it)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun fetchOrders() {
        viewModel.fetchOrders()
    }

    private fun setupRecyclerView() {
        binding.rvOrderMenu.apply {
            layoutManager = LinearLayoutManager(this@CheckoutOrderActivity)
            adapter = checkoutOrderAdapter
        }
    }
}