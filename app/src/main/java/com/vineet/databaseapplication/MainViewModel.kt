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
    var city                = ""

    var errorMsgUserName    = MutableLiveData<String?>()
    var errorMsgEmail       = MutableLiveData<String?>()
    var errorMsgMobile      = MutableLiveData<String?>()
    var errorMsgCity        = MutableLiveData<String?>()

    private val database: AppDatabase = AppDatabase.getDatabase(application)
    private val repository: MainRepository = MainRepository()
    private val userDao: UserDao = database.getUserDao()

    val liveDataUserList: LiveData<List<UserEntity>> = repository.getAllUsers(database.getUserDao())
    val liveDataError = MutableLiveData<String>()
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
            val userEntity = UserEntity(name = userName, email = email, mobile = mobile, city = city)
            addUser(userEntity)
            liveDataRefreshList.value = ""
        }
    }

    private fun validate() : Boolean {
        if (userName.isBlank()) {
            errorMsgUserName.value = "Enter User Name"
            errorMsgEmail.value = null
            errorMsgMobile.value = null
            errorMsgCity.value = null
            liveDataError.value = "Enter User Name"
            return false
        }
        if (email.isBlank()) {
            errorMsgUserName.value = null
            errorMsgEmail.value = "Enter Email"
            errorMsgMobile.value = null
            errorMsgCity.value = null
            liveDataError.value = "Enter Email"
            return false
        }
        if (mobile.isBlank()) {
            errorMsgUserName.value = null
            errorMsgEmail.value = null
            errorMsgMobile.value = "Enter Mobile Number"
            errorMsgCity.value = null
            liveDataError.value = "Enter Mobile Number"
            return false
        }
        if (city.isBlank()) {
            errorMsgUserName.value = null
            errorMsgEmail.value = null
            errorMsgMobile.value = null
            errorMsgCity.value = "Enter City"
            liveDataError.value = "Enter City"
            return false
        }
        return true
    }

}