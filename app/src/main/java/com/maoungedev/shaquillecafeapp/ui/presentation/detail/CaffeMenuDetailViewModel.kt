package com.maoungedev.shaquillecafeapp.ui.presentation.detail

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import com.maoungedev.shaquillecafeapp.ui.model.CaffeMenuData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CaffeMenuDetailViewModel @Inject constructor() : ViewModel() {

    private val caffe = MutableStateFlow<CaffeMenuData>(CaffeMenuData())
    val mCaffe: StateFlow<CaffeMenuData> get() = caffe

    fun processIntent(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            caffe.value = intent.extras?.getParcelable(
                CaffeMenuDetailActivity.CAFFE_MENU,
                CaffeMenuData::class.java
            )!!
        } else {
            caffe.value = intent.getParcelableExtra(CaffeMenuDetailActivity.CAFFE_MENU)!!
        }
    }

}
