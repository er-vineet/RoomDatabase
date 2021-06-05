package com.vineet.databaseapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(userList: List<UserEntity>)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("Select * from user_table where mobile =  :mobile")
    fun getUser(mobile: String): UserEntity

    @Query("Select * from user_table")
    fun getAllUsers(): LiveData<List<UserEntity>>

}