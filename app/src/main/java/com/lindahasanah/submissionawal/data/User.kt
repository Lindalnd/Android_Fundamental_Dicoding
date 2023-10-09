package com.lindahasanah.submissionawal.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id :String,
    val username : String,
    val name : String,
    val followers : Int,
    val following : Int,
    val image : String
) : Parcelable
