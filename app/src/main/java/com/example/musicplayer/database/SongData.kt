package com.example.musicplayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "songs_table",indices = arrayOf(Index(value = ["song_uri","song_name","image_uri"],unique = true)))
data class SongData(
        @ColumnInfo(name = "song_name")
    val title : String,
        @ColumnInfo(name="song_uri")
    val songUri : String,
        @ColumnInfo(name="image_uri")
    val imageUri : String,
        @PrimaryKey(autoGenerate = true)
        val id:Long=0L,
        @ColumnInfo(name = "favourite")
        var fav : Boolean = false )
