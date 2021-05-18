package com.example.musicplayer.mainScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.database.SongDao

class MainViewModelFactory(private val dataSource: SongDao, private val app : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dataSource,app) as T
        }
        throw IllegalArgumentException("gibberish rubbish")
    }
}