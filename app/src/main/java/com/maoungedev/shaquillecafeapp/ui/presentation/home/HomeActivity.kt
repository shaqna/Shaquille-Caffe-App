package com.maoungedev.shaquillecafeapp.ui.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maoungedev.shaquillecafeapp.databinding.ActivityHomeBinding
import com.maoungedev.shaquillecafeapp.ui.presentation.caffe.CaffeMenuActivity
import com.maoungedev.shaquillecafeapp.ui.presentation.caffe.CaffeMenuCategoryActivity
import com.maoungedev.shaquillecafeapp.ui.presentation.kitchen.KitchenActivity
import com.maoungedev.shaquillecafeapp.ui.presentation.order.OrderActivity
import com.maoungedev.shaquillecafeapp.utils.Constants

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onActionButton()
    }

    private fun onActionButton() {
        binding.apply {
            btnMenuCaffe.setOnClickListener {
                Intent(this@HomeActivity, CaffeMenuCategoryActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.apply {
            btnKitchen.setOnClickListener {
                Intent(this@HomeActivity, KitchenActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        binding.apply {
            btnOrder.setOnClickListener {
                Intent(this@HomeActivity, OrderActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


}