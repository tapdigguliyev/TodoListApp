package com.example.listmaker.listmaker

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var list: TaskList
    private lateinit var taskListRecyclerView: RecyclerView
    private lateinit var listDataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)

        arguments?.let {
            val args = TaskDetailFragmentArgs.fromBundle(it)
            list = listDataManager.readLists().filter { list -> list.name == args.listString }[0] //wtf is going on here
        }

        activity?.let {
            taskListRecyclerView = binding.rvTaskList
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
            taskListRecyclerView.adapter = TaskListAdapter(list)

            (activity as MainActivity).setToolbarTitle(list.name)

            binding.fabAdd.setOnClickListener {
                showCreateTaskListDialog()
            }
        }
    }

    private fun showCreateTaskListDialog() {
        activity?.let {
            val taskListEditText = EditText(it)
            taskListEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

            AlertDialog.Builder(it)
                .setTitle(getString(R.string.task_to_add))
                .setView(taskListEditText)
                .setPositiveButton(R.string.task_add) {
                        dialog, _ ->
                    val task = taskListEditText.text.toString()
                    list.tasks.add(task)
                    listDataManager.saveLists(list)
                    dialog.dismiss()
                }
                .create().show()
        }
    }
}