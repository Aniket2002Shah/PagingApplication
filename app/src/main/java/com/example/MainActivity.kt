package com.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.load.LoadingPageAdapter
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    lateinit var viewModel: QuoteViewModel
    lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        quoteAdapter = QuoteAdapter()
        viewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = quoteAdapter.withLoadStateHeaderAndFooter(
            header = LoadingPageAdapter(),
            footer = LoadingPageAdapter()
        )
        viewModel.list.observe(this) {
            quoteAdapter.submitData(lifecycle, it)
        }

    }
}