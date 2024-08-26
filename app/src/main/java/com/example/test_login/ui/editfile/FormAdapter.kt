package com.example.test_login.ui.editfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFormBinding

class FormAdapter : RecyclerView.Adapter<FormAdapter.FormViewHolder>() {

    private var items: List<FormItem> = listOf()
    private var onItemClickListener: ((FormItem) -> Unit)? = null
    private var onItemLongClickListener: ((FormItem) -> Boolean)? = null

    fun submitList(newList: List<FormItem>) {
        items = newList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (FormItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (FormItem) -> Boolean) {
        onItemLongClickListener = listener
    }

    fun updateItem(position: Int, updatedItem: FormItem) {
        items = items.toMutableList().apply {
            set(position, updatedItem)
        }
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        items = items.toMutableList().apply {
            removeAt(position)
        }
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val binding = ItemFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FormViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(item) ?: false
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FormViewHolder(private val binding: ItemFormBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem) {
            binding.formItem = item
            binding.executePendingBindings()
        }
    }
}
