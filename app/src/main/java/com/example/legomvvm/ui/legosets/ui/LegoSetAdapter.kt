package com.example.legomvvm.ui.legosets.ui

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.legomvvm.databinding.ListItemSetsBinding
import com.example.legomvvm.ui.legosets.data.LegoSet

class LegoSetAdapter :
    PagedListAdapter<LegoSet, LegoSetAdapter.ViewHolder>(LegoSettDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val legoSet = getItem(position)
        legoSet?.let {
            holder.apply {
                bind(legoSet, isGridLayoutManager())
                itemView.tag = legoSet
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemSetsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    class ViewHolder(private val binding: ListItemSetsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: LegoSet,
            isGridLayoutManager: Boolean
        ) {
            binding.apply {
                legoSet = item
                title.visibility = if (isGridLayoutManager) GONE else VISIBLE
                executePendingBindings()
            }
        }
    }
}

private class LegoSettDiffCallback : DiffUtil.ItemCallback<LegoSet>() {
    override fun areItemsTheSame(oldItem: LegoSet, newItem: LegoSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LegoSet, newItem: LegoSet): Boolean {
        return oldItem == newItem
    }
}