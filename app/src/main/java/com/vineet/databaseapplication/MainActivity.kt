package com.vineet.databaseapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vineet.databaseapplication.database.UserEntity
import com.vineet.databaseapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapterMain: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDataBindingAndViewModel()
        setRecyclerView()
    }

    private fun setDataBindingAndViewModel() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        mainViewModel.liveDataRefreshList.observe(this, { adapterMain.refreshData() })
        mainViewModel.liveDataUserList.observe(this, { adapterMain.setUserList(it) })
    }

    private fun setRecyclerView() {
        adapterMain = MainAdapter(this, ::onRowClick, ::onDeleteRowClick)
        rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = adapterMain
        }
    }

    private fun onRowClick(mobileNumber: String) {
        if (mobileNumber.isNotBlank())
            Toast.makeText(this, mobileNumber, Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteRowClick(userEntity: UserEntity) {
        mainViewModel.deleteUser(userEntity)
    }

}
