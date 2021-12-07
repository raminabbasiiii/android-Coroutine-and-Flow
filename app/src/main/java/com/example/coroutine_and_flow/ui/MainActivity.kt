package com.example.coroutine_and_flow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutine_and_flow.databinding.ActivityMainBinding
import com.example.coroutine_and_flow.util.ConnectivityManager
import com.example.coroutine_and_flow.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var countryListAdapter: CountryListAdapter? = null

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        connectivityManager.isNetworkAvailable.observe(this) {
            if (it) {
                Toast.makeText(this,"connected",Toast.LENGTH_SHORT).show()
                mainViewModel.countries.observe(this) { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            countryListAdapter?.apply {
                                resource.data?.let { it1 -> setCountryList(it1) }
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(this,resource.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else {
                Toast.makeText(this,"disconnected",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun configureRecyclerView() {
        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            countryListAdapter = CountryListAdapter()
            adapter = countryListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }
}