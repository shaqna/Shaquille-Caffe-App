package com.maoungedev.shaquillecafeapp.ui.presentation.caffe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maoungedev.shaquillecafeapp.databinding.ItemMenuCaffeBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.utils.Helper

class CaffeMenuAdapter : RecyclerView.Adapter<CaffeMenuAdapter.ViewHolder>() {


    private var onItemTapListener: OnItemTapListener? = null

    private val listCaffeMenu = arrayListOf<CaffeMenu>()

    fun setItemList(list: List<CaffeMenu>) {
        listCaffeMenu.clear()
        listCaffeMenu.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMenuCaffeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listCaffeMenu.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCaffeMenu[position])
    }

    inner class ViewHolder(private val binding: ItemMenuCaffeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CaffeMenu) {
            with(binding) {
                tvCaffeMenuName.text = item.menuCaffeName
                tvCaffeMenuDescription.text = item.description
                tvCaffeMenuPrice.text = Helper.generateTextPrice(item.price)

                Glide.with(itemView.context).load(Helper.generateImage(item.photo))
                    .into(ivCaffeMenuPhoto)
            }

            itemView.setOnClickListener {
                onItemTapListener?.onTap(item)
            }
        }
    }

    fun setOnItemTap(onItemTap: (itemTapped: CaffeMenu) -> Unit) {
        onItemTapListener = object : OnItemTapListener {
            override fun onTap(caffeMenu: CaffeMenu) {
                onItemTap(caffeMenu)
            }

        }
    }

    interface OnItemTapListener {
        fun onTap(caffeMenu: CaffeMenu)
    }
}