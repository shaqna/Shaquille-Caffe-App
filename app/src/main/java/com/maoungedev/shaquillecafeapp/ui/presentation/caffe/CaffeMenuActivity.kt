package com.maoungedev.shaquillecafeapp.ui.presentation.caffe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maoungedev.shaquillecafeapp.databinding.ActivityCaffeMenuBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.ui.mapper.UiDataMapper
import com.maoungedev.shaquillecafeapp.ui.presentation.detail.CaffeMenuDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CaffeMenuActivity : AppCompatActivity() {

    private val binding: ActivityCaffeMenuBinding by lazy {
        ActivityCaffeMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: CaffeViewModel by viewModels()


    private val caffeMenuAdapter: CaffeMenuAdapter by lazy {
        CaffeMenuAdapter().apply {
            setOnItemTap { caffeMenu ->
                Intent(this@CaffeMenuActivity, CaffeMenuDetailActivity::class.java).also {
                    it.putExtra(
                        CaffeMenuDetailActivity.CAFFE_MENU,
                        UiDataMapper.mapDomainToPresentationData(caffeMenu)
                    )
                    startActivity(it)
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        processIntent()
        setupRecyclerView()
        fetchCaffeMenu()
        observe()
    }

    private fun observe() {
        observeState()
        observeCaffeMenu()
    }

    private fun observeCaffeMenu() {
        viewModel.mCaffeMenu.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { list ->
                handleCaffeMenu(list)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleCaffeMenu(list: List<CaffeMenu>) {
        caffeMenuAdapter.setItemList(list)
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: CaffeMenuUiState) {
        when(state) {
            CaffeMenuUiState.Init -> Unit
            is CaffeMenuUiState.IsLoading -> showLoading(state.isLoading)
            is CaffeMenuUiState.ShowToast -> showToast(state.message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this , message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun fetchCaffeMenu() {
        viewModel.fetchCaffeMenuByCategory()
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvCaffeMenu.apply {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = caffeMenuAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun processIntent() {
        viewModel.processIntent(intent)
    }

    companion object {
        const val CATEGORY = "menu_category"
    }
}