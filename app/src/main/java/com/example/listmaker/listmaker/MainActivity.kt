package com.example.listmaker.listmaker

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoListRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoListRecyclerView = findViewById(R.id.rvTodoList)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = TodoListAdapter()

        binding.fabCreate.setOnClickListener {
            showCreateTodoListDialog()
        }
    }

    private fun showCreateTodoListDialog() {
        val adapter = todoListRecyclerView.adapter as TodoListAdapter
        val builder = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        builder.setTitle(getString(R.string.name_of_list))
        builder.setView(todoTitleEditText)
        builder.setPositiveButton(getString(R.string.create_list)) {
                dialog, _ ->
            adapter.addNewItem(todoTitleEditText.text.toString())
            dialog.dismiss()
        }
        builder.create().show()
    }
}