package com.maoungedev.shaquillecafeapp.ui.presentation.kitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maoungedev.shaquillecafeapp.databinding.ActivityKitchenBinding
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class KitchenActivity : AppCompatActivity() {
    private val binding: ActivityKitchenBinding by lazy {
        ActivityKitchenBinding.inflate(layoutInflater)
    }

    private val kitchenAdapter: KitchenAdapter by lazy {
        KitchenAdapter()
    }

    private val alertDialogBuilder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(this)
    }

    private val viewModel: KitchenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAlertDialog()
        setupRecyclerView()
        fetchKitchenQueue()
        observe()
        onDeleteItem()
    }

    private fun setupAlertDialog() {
        alertDialogBuilder.setMessage("Apakah anda ingin menghapus item ini?")
        alertDialogBuilder.setTitle("Hapus Item")
        alertDialogBuilder.setCancelable(false)

    }

    private fun onDeleteItem() {
        kitchenAdapter.onItemDeleteMenuClick = {
            alertDialogBuilder.setPositiveButton("Ya") { dialog, which ->
                viewModel.deleteOrder(it)
                observeKitchenQueue()
            }

            alertDialogBuilder.setNegativeButton("Tidak") { dialog, which ->
                dialog.cancel()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun observe() {
        observeState()
        observeKitchenQueue()
    }

    private fun observeKitchenQueue() {
        viewModel.mKitchenQueue.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleKitchenQueue(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleKitchenQueue(it: List<OrderMenu>) {
        kitchenAdapter.setListItem(it)
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                handleState(it)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(it: KitchenUiState) {
        when(it) {
            KitchenUiState.Init -> Unit
            is KitchenUiState.IsLoading -> handleLoading(it.isLoading)
            is KitchenUiState.ShowToast -> showToast(it.message)
        }
    }

    private fun fetchKitchenQueue() {
        viewModel.fetchKitchenQueue()
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

    private fun setupRecyclerView() {
        binding.rvKitchen.apply {
            layoutManager = LinearLayoutManager(this@KitchenActivity)
            adapter = kitchenAdapter
        }
    }
}