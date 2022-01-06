package com.example.shadiofflinedataapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shadiofflinedataapp.R
import com.example.shadiofflinedataapp.databinding.ActivityMainBinding
import com.example.shadiofflinedataapp.ui.adapters.MatchedUserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()
    private lateinit var userAdapter: MatchedUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)//ActivityMainBinding.inflate(layoutInflater)
        //  setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        userAdapter = MatchedUserAdapter(viewModel)

        binding.matchedUsersRecyclerView.adapter = userAdapter
        binding.matchedUsersRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.matchedUserList.observe(this, {
            Log.d(TAG, "onCreate: AK: ${it.size}")
            userAdapter.submitList(it)
        })

        viewModel.acceptedRejectedUsers.observe(this, {
            Log.d(TAG, "onCreate: acceptedRejectedUsers: AK: ${it.size}")
            userAdapter.setAcceptRejectUserList(it)
        })
    }
}