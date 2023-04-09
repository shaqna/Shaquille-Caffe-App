package com.maoungedev.shaquillecafeapp.ui.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maoungedev.shaquillecafeapp.databinding.ItemOrderCaffeMenuBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.utils.Helper

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private val listOrder = arrayListOf<CaffeMenu>()
    private var onButtonOrderClickListener: OnButtonOrderClickListener? = null

    fun setListItem(items: List<CaffeMenu>) {
        listOrder.clear()
        listOrder.addAll(items)
        notifyDataSetChanged()
    }

    fun setButtonOrderClickListener(onButtonClick: (menuId: String) -> Unit) {
        onButtonOrderClickListener = object : OnButtonOrderClickListener {
            override fun onClick(menuId: String) {
                onButtonClick(menuId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemOrderCaffeMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = listOrder.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }

    inner class ViewHolder(private val binding: ItemOrderCaffeMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CaffeMenu) {
            with(binding) {
                tvCaffeMenuOrderName.text = item.menuCaffeName
                tvMenuCaffePriceOrder.text = Helper.generateTextPrice(item.price)
                tvMenuCaffeCategoryOrder.text = item.category

                Glide.with(itemView.context).load(Helper.generateImage(item.photo)).into(imageView)

                btnOrder.setOnClickListener {
                    onButtonOrderClickListener?.onClick(item.idMenu)
                }
            }
        }
    }

    interface OnButtonOrderClickListener {
        fun onClick(menuId: String)
    }

}