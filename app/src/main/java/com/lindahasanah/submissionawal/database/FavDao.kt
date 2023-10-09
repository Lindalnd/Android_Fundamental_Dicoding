package com.lindahasanah.submissionawal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Query("SELECT * FROM fav_user")
    fun getFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM fav_user ORDER BY username ASC")
    fun loadAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM fav_user WHERE id =:id")
    suspend fun checkFav(id: Int):Int

    @Query("DELETE FROM fav_user WHERE id = :id")
    fun deleteFromFav(id: Int) : Int


}