package com.example.musicplayer.playPge

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.database.SongDao

class PlayFragmentFactory(private val dataSource: SongDao,private val app : Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayFragmentViewModel::class.java)){
            return PlayFragmentViewModel(dataSource,app) as T
        }
        throw IllegalArgumentException("gibberish play model")
    }
}