package com.vineet.databaseapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vineet.databaseapplication.database.UserEntity
import com.vineet.databaseapplication.databinding.ItemRowBinding

class MainAdapter(private val context: Context, private val onClickRow:(String)->Unit, private val onDeleteClick:(UserEntity)->Unit)
    : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    private var listUsers: List<UserEntity>? = null

    class MainViewHolder(val itemRowBinding: ItemRowBinding) : RecyclerView.ViewHolder(itemRowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding: ItemRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_row, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.itemRowBinding.user = listUsers?.get(position)
        holder.itemRowBinding.llRow.setOnClickListener { onClickRow(listUsers?.get(position)?.mobile ?: "") }
        holder.itemRowBinding.ivTrash.setOnClickListener {
            val user = listUsers?.get(position)
            user?.let {
                onDeleteClick(it)
            }
        }
    }

    fun setUserList(list: List<UserEntity>) {
        listUsers = list
        notifyDataSetChanged()
    }

    fun refreshData() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listUsers?.size ?: 0
    }
}