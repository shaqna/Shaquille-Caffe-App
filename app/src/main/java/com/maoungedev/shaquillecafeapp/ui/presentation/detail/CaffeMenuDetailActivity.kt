package com.maoungedev.shaquillecafeapp.ui.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.maoungedev.shaquillecafeapp.databinding.ActivityDetailCaffeMenuBinding
import com.maoungedev.shaquillecafeapp.ui.model.CaffeMenuData
import com.maoungedev.shaquillecafeapp.utils.Helper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CaffeMenuDetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailCaffeMenuBinding by lazy {
        ActivityDetailCaffeMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: CaffeMenuDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        processIntent()
        observe()
    }

    private fun observe() {
        viewModel.mCaffe.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { caffeDetail ->
                handleCaffeDetail(caffeDetail)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleCaffeDetail(caffeDetail: CaffeMenuData) {
        with(binding) {
            tvCaffeMenuName.text = caffeDetail.menuCaffeName
            tvCaffeMenuPrice.text = "Rp.${caffeDetail.price}"
            tvCaffeMenuDescription.text = caffeDetail.description

            Glide.with(this@CaffeMenuDetailActivity).load(Helper.generateImage(caffeDetail.photo))
                .into(imgCaffeMenu)
        }
    }

    private fun processIntent() {
        viewModel.processIntent(intent)
    }

    companion object {
        const val CAFFE_MENU = "caffe_menu_data"
    }
}