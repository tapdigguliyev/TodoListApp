package com.example.listmaker.listmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.listmaker.listmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setToolbarTitle(getString(R.string.app_name))
    }

    fun setToolbarTitle(name: String) {
        binding.toolbar.title = name
    }
}