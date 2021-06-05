package com.vineet.databaseapplication.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(

    val name: String,
    val email: String,
    @PrimaryKey(autoGenerate = false)
    val mobile: String,
    val city: String?
) {
    @Ignore
    fun getUserDetails() : String {
        return "Email: $email\nMobile: $mobile\nCity: $city"
    }
}
