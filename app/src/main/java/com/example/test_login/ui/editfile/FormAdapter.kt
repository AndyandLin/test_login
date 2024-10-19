package com.example.test_login.ui.editfile

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFormBinding

class FormAdapter(
    private val onEditClick: (FormItem) -> Unit,
    private val onDeleteClick: (FormItem) -> Unit,
    private val onDownloadClick: (FormItem) -> Unit
) : RecyclerView.Adapter<FormAdapter.FormViewHolder>() {

    private var items: List<FormItem> = listOf()  // 存放表單項目的列表

    // 提交新的表單列表
    fun submitList(newList: List<FormItem>) {
        items = newList
        notifyDataSetChanged()
    }

    // 創建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val binding = ItemFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FormViewHolder(binding)
    }

    // 綁定數據到 ViewHolder
    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // 設置編輯圖示的點擊事件
        holder.binding.editImageView.setOnClickListener {
            animateView(it)  // 僅縮放編輯圖示
            onEditClick(item)
        }
        // 設置刪除圖示的點擊事件
        holder.binding.deleteImageView.setOnClickListener {
            animateView(it)  // 僅縮放刪除圖示
            onDeleteClick(item)
        }
        // 設置下載圖示的點擊事件
        holder.binding.downloadImageView.setOnClickListener {
            animateView(it)  // 僅縮放下載圖示
            onDownloadClick(item)
        }
    }

    // 返回表單項目的數量
    override fun getItemCount(): Int {
        return items.size
    }

    // ViewHolder 類別，負責顯示表單數據
    class FormViewHolder(val binding: ItemFormBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem) {
            binding.formItem = item  // 將表單項目綁定到佈局
            binding.executePendingBindings()  // 更新佈局
        }
    }

    // 動畫效果：縮放視圖
    private fun animateView(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.85f, 1.0f)  // 縮放到 85%
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.85f, 1.0f)  // 縮放到 85%
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleX).with(scaleY)
        animatorSet.duration = 200  // 動畫持續時間
        animatorSet.start()
    }
}