package com.lindahasanah.submissionawal.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_user")
data class FavoriteUser(
    @field:ColumnInfo(name = "username") var username: String = "",

    @field:ColumnInfo(name = "id") @field:PrimaryKey var id: Int? = null,

    @field:ColumnInfo(name = "avatarUrl") var avatarUrl: String? = null,

    )