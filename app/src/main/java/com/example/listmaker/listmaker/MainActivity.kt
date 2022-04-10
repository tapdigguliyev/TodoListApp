package com.example.listmaker.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TodoListFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private val todoListAdapter = TodoListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabCreate.setOnClickListener {
            showCreateTodoListDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                todoListAdapter.saveList(list)
            }
        }
    }

    private fun showCreateTodoListDialog() {
        val builder = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        builder.setTitle(getString(R.string.name_of_list))
        builder.setView(todoTitleEditText)
        builder.setPositiveButton(getString(R.string.create_list)) {
                dialog, _ ->
            val list = TaskList(todoTitleEditText.text.toString())
            todoListAdapter.addList(list)
            dialog.dismiss()
            showTaskListItems(list)
        }
        builder.create().show()
    }

    private fun showTaskListItems(list: TaskList) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(INTENT_LIST_KEY, list)
        }
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onTodoListClicked(list: TaskList) {
        showTaskListItems(list)
    }
}