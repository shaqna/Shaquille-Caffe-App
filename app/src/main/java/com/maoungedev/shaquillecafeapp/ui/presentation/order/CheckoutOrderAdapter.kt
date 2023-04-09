package com.maoungedev.shaquillecafeapp.ui.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maoungedev.shaquillecafeapp.databinding.ItemOrderCaffeMenuBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu
import com.maoungedev.shaquillecafeapp.utils.DiffCallback
import com.maoungedev.shaquillecafeapp.utils.Helper

class CheckoutOrderAdapter : RecyclerView.Adapter<CheckoutOrderAdapter.ViewHolder>() {

    private var listOrder = arrayListOf<OrderMenu>()
    private var onButtonCancelClickListener: OnButtonCancelClickListener? = null


    fun setListItem(items: List<OrderMenu>) {
//        val diffCallback = DiffCallback(listOrder, items)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        listOrder = items
//        diffResult.dispatchUpdatesTo(this)
        listOrder.clear()
        listOrder.addAll(items)
        notifyDataSetChanged()
    }

    fun setButtonCancelClickListener(onButtonClick: (orderId: String) -> Unit) {
        onButtonCancelClickListener = object : OnButtonCancelClickListener {
            override fun onClick(orderId: String) {
                onButtonClick(orderId)
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
        if(!listOrder[position].isDeleted) {
            holder.bind(listOrder[position], position)
        }

    }

    inner class ViewHolder(private val binding: ItemOrderCaffeMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderMenu, pos: Int) {
            with(binding) {
                tvCaffeMenuOrderName.text = item.menuCaffeName
                tvMenuCaffePriceOrder.text = Helper.generateTextPrice(item.price)
                tvMenuCaffeCategoryOrder.text = item.category

                Glide.with(itemView.context).load(Helper.generateImage(item.photo)).into(imageView)

                btnOrder.apply {
                    text = "Batalkan"
                    setOnClickListener {
                        //SlistOrder.removeAt(pos)
                         //item.isDeleted = true
                        notifyDataSetChanged()
                        onButtonCancelClickListener?.onClick(item.orderId)
                    }
                }
            }
        }
    }

    interface OnButtonCancelClickListener {
        fun onClick(orderId: String)
    }
}