package com.example.musicplayer.mainScreen

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.SongAdapter
import com.example.musicplayer.database.SongData


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SongData>?) {
    val adapter = recyclerView.adapter as SongAdapter
    adapter.submitList(data)
}