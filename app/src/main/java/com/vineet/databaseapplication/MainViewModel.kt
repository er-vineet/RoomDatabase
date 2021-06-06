package com.vineet.databaseapplication

import android.app.Application
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vineet.databaseapplication.database.AppDatabase
import com.vineet.databaseapplication.database.UserDao
import com.vineet.databaseapplication.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var userName            = ""
    var email               = ""
    var mobile              = ""

    var errorMsgUserName    = MutableLiveData<String?>()
    var errorMsgEmail       = MutableLiveData<String?>()
    var errorMsgMobile      = MutableLiveData<String?>()

    private val database: AppDatabase = AppDatabase.getDatabase(application)
    private val repository: MainRepository = MainRepository()
    private val userDao: UserDao = database.getUserDao()

    val liveDataUserList: LiveData<List<UserEntity>> = repository.getAllUsers(database.getUserDao())
    val liveDataRefreshList = MutableLiveData<String>()

    private fun addUser(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userDao, userEntity)
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userDao, userEntity)
            withContext(Dispatchers.Main) {
                liveDataRefreshList.value = ""
            }
        }
    }

    fun onClickSave() {
        if (validate()) {
            val userEntity = UserEntity(name = userName, email = email, mobile = mobile)
            addUser(userEntity)
            liveDataRefreshList.value = ""
        }
    }

    private fun validate() : Boolean {
        errorMsgUserName.value  = null
        errorMsgEmail.value     = null
        errorMsgMobile.value    = null

        if (userName.isBlank()) {
            errorMsgUserName.value  = "Enter User Name"
            return false
        }
        if (email.isBlank()) {
            errorMsgEmail.value     = "Enter Email"
            return false
        }
        if (mobile.isBlank()) {
            errorMsgMobile.value    = "Enter Mobile Number"
            return false
        }
        return true
    }

}