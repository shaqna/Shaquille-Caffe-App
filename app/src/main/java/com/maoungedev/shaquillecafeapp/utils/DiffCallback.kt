package com.maoungedev.shaquillecafeapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu

class DiffCallback(
    private val oldList: List<OrderMenu>,
    private val newList: List<OrderMenu>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].orderId ==  newList[newItemPosition].orderId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}