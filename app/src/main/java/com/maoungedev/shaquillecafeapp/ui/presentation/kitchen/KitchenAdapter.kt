package com.maoungedev.shaquillecafeapp.ui.presentation.kitchen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maoungedev.shaquillecafeapp.databinding.ItemKitchenBinding
import com.maoungedev.shaquillecafeapp.domain.model.CaffeMenu
import com.maoungedev.shaquillecafeapp.domain.model.OrderMenu

class KitchenAdapter: RecyclerView.Adapter<KitchenAdapter.ViewHolder>() {

    private val listOrder = arrayListOf<OrderMenu>()
    var onItemDeleteMenuClick: ((String) -> Unit)? = null

    fun setListItem(items: List<OrderMenu>) {
        listOrder.clear()
        listOrder.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemKitchenBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderMenu) {
            with(binding) {
                tvDateTime.text = item.time
                tvMenuName.text = item.menuCaffeName
                tvTableNumber.text = item.tableNumber
            }

            itemView.setOnClickListener {
                onItemDeleteMenuClick?.invoke(item.orderId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemKitchenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = listOrder.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }
}