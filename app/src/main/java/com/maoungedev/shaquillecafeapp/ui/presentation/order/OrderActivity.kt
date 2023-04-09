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
import com.maoungedev.shaquillecafeapp.databinding.ActivityOrderBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private val binding: ActivityOrderBinding by lazy {
        ActivityOrderBinding.inflate(layoutInflater)
    }

    private val viewModel: OrderViewModel by viewModels()

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter().apply {
            setButtonOrderClickListener { menuId ->
                doOrder(menuId)
            }
        }
    }

    private fun doOrder(menuId: String) {
        val tableNumber = binding.edtTableNumber.text
        if (tableNumber.isNullOrEmpty()) {
            showError()
        } else {
            viewModel.setTableNumber(tableNumber.toString())
            viewModel.addOrder(menuId)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        fetchAllCaffeMenu()
        observe()
        onButtonAction()
    }

    private fun setupRecyclerView() {
        binding.rvCaffeMenu.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = orderAdapter
        }
    }

    private fun fetchAllCaffeMenu() {
        viewModel.fetchAllCaffeMenu()
    }

    private fun observe() {
        observeState()
        observeMessage()
        observeMenuCaffe()
    }

    private fun observeMessage() {
        viewModel.mMessage.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleMessage(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleMessage(it: String) {
        showToast(it)
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleState(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(it: OrderUiState) {
        when (it) {
            OrderUiState.Init -> Unit
            is OrderUiState.IsLoading -> handleLoading(it.isLoading)
            is OrderUiState.ShowToast -> showToast(it.message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun observeMenuCaffe() {
        viewModel.mCaffeMenu.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleCaffeMenu(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleCaffeMenu(it: List<CaffeMenu>) {
        orderAdapter.setListItem(it)
    }

    private fun onButtonAction() {
        binding.apply {
            btnCheckOrder.setOnClickListener {
                startActivity(Intent(this@OrderActivity, CheckoutOrderActivity::class.java))
            }
        }
    }

    private fun showError() {
        binding.edtTableNumber.error = "Tidak Boleh Kosong"
    }
}