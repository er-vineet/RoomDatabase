package com.vineet.databaseapplication

import android.util.Log
import androidx.lifecycle.LiveData
import com.vineet.databaseapplication.database.UserDao
import com.vineet.databaseapplication.database.UserEntity

class MainRepository {

    fun getAllUsers(userDao: UserDao) : LiveData<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun addUser(userDao: UserDao, userEntity: UserEntity) {
        Log.i("log", "Add User")
        userDao.insertUser(userEntity)
    }

    suspend fun deleteUser(userDao: UserDao, userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }

}