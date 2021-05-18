package com.example.musicplayer.mainScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.database.SongDao
import com.example.musicplayer.database.SongData
import com.mtechviral.mplaylib.MusicFinder
import kotlinx.coroutines.*

class MainViewModel(private val dataSource : SongDao, private val app : Application) : ViewModel() {

    val viewModelJob = Job()
    val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    lateinit var songs : MutableList<MusicFinder.Song>

    private var _playList = MutableLiveData<List<SongData>>()

    val playList : LiveData<List<SongData>>
    get() = _playList

    init{
        getPlaylist()
        Log.i("viewmodel","happened")
    }


    fun insert() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                songs.forEach {
                    val song = SongData(it.title,it.uri.toString(),it.albumArt.toString())
                    dataSource.insert(song)
                    Log.i("check",it.title)
                }
            }
        }
    }

    private  fun getPlaylist() {
        viewModelScope.launch {
            _playList.value=dataSource.display()
            songs = getSongs()
            insert()
        }
    }

     fun getPlaylistFav(){
        viewModelScope.launch {
            _playList.value=dataSource.getFavourites()
        }
    }

     fun getPlaylistAll(){
        viewModelScope.launch {
            _playList.value=dataSource.display()
        }
    }

    private suspend fun getSongs(): MutableList<MusicFinder.Song> {
        val songFinder = MusicFinder(app.contentResolver)
        songFinder.prepare()
        return withContext(Dispatchers.Default){
            songFinder.allSongs
        }
    }


}