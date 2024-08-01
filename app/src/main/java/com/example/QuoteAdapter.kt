package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.model.Result
import com.example.myapplication.R

class QuoteAdapter: PagingDataAdapter<com.example.model.Result,QuoteAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(item:View):RecyclerView.ViewHolder(item){
        val quote: TextView = itemView.findViewById<TextView>(R.id.textView)
    }

    companion object{
        private val COMPARATOR = object:DiffUtil.ItemCallback<com.example.model.Result>(){
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem==newItem
            }

            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.quote.text = item.content
        }
    }

}