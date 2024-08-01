package com.example.load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class LoadingPageAdapter:LoadStateAdapter<LoadingPageAdapter.LoadViewHolder>() {

    class LoadViewHolder(item: View):RecyclerView.ViewHolder(item){
        val bar = item.findViewById<ProgressBar>(R.id.progress_Bar)

        fun bind(loadState: LoadState){
            bar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val state = LayoutInflater.from(parent.context).inflate(R.layout.item_loader,parent,false)
        return LoadViewHolder(state)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}