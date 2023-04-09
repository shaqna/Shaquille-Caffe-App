package com.maoungedev.shaquillecafeapp.ui.presentation.caffe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maoungedev.shaquillecafeapp.databinding.ActivityCaffeMenuCategoryBinding
import com.maoungedev.shaquillecafeapp.utils.Constants

class CaffeMenuCategoryActivity : AppCompatActivity() {

    private val binding: ActivityCaffeMenuCategoryBinding by lazy {
        ActivityCaffeMenuCategoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onActionButton()
    }

    private fun onActionButton() {
        binding.apply {
            btnFood.setOnClickListener {
                Intent(this@CaffeMenuCategoryActivity, CaffeMenuActivity::class.java).also {
                    it.putExtra(CaffeMenuActivity.CATEGORY, Constants.Category.FOOD)
                    startActivity(it)
                }
            }

            btnDrink.setOnClickListener {
                Intent(this@CaffeMenuCategoryActivity, CaffeMenuActivity::class.java).also {
                    it.putExtra(CaffeMenuActivity.CATEGORY, Constants.Category.DRINK)
                    startActivity(it)
                }
            }
            btnDessert.setOnClickListener {
                Intent(this@CaffeMenuCategoryActivity, CaffeMenuActivity::class.java).also {
                    it.putExtra(CaffeMenuActivity.CATEGORY, Constants.Category.DESSERT)
                    startActivity(it)
                }
            }
        }

    }
}