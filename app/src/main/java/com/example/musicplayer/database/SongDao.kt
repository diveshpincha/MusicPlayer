package com.example.musicplayer.database

import androidx.room.*

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(song : SongData)
    @Update
    suspend fun update(song : SongData)
    @Query("select * from songs_table")
    suspend fun display(): List<SongData>
    @Query("select * from songs_table where favourite = 1")
    suspend fun getFavourites():List<SongData>
}