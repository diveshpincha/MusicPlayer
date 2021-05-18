package com.example.musicplayer.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SongData::class],version = 6,exportSchema = false)
abstract class SongDataBase : RoomDatabase() {
    abstract val songDao : SongDao
    companion object{
        @Volatile
        private var INSTANCE :SongDataBase?=null

        fun getInstance(context: Context):SongDataBase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    Log.i("timepass","db was empty")
                    instance=
                            Room.databaseBuilder(context.applicationContext,SongDataBase::class.java,"songs_database")
                                    .fallbackToDestructiveMigration()
                                    .build()
                    INSTANCE=instance
                }


                return instance
            }
        }
    }
}