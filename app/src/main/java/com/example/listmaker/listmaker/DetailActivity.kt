package com.example.listmaker.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listmaker.listmaker.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = intent.getParcelableExtra(INTENT_LIST_KEY)!!
        binding.toolbar.title = list.name
    }
}