package com.example.listmaker.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var list: TaskList
    private lateinit var taskListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = intent.getParcelableExtra(INTENT_LIST_KEY)!!
        binding.toolbar.title = list.name

        taskListRecyclerView = binding.rvTaskList
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = TaskListAdapter(list)

        binding.fabAdd.setOnClickListener {
            showCreateTaskListDialog()
        }
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

    private fun showCreateTaskListDialog() {
        val taskListEditText = EditText(this)
        taskListEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.task_to_add))
            .setView(taskListEditText)
            .setPositiveButton(R.string.task_add) {
                dialog, _ ->
                    val task = taskListEditText.text.toString()
                    list.tasks.add(task)
                    dialog.dismiss()
            }
            .create().show()
    }
}