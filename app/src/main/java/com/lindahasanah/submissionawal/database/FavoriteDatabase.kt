package com.lindahasanah.submissionawal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)

abstract class FavoriteDatabase : RoomDatabase(){
    companion object{
        var INSTANCE : FavoriteDatabase?= null

        fun getDataase(context: Context): FavoriteDatabase?{
            if(INSTANCE==null){
                synchronized(FavoriteDatabase::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "favDatabase").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favoriteDao(): FavDao
}