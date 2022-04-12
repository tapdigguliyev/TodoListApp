package com.example.listmaker.listmaker

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding

    private lateinit var list: TaskList
    private lateinit var taskListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getParcelable(ARG_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            taskListRecyclerView = binding.rvTaskList
            taskListRecyclerView.layoutManager = LinearLayoutManager(it)
            taskListRecyclerView.adapter = TaskListAdapter(list)
        }

        binding.fabAdd.setOnClickListener {
            showCreateTaskListDialog()
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
                    dialog.dismiss()
                }
                .create().show()
        }
    }

    companion object {
        fun newInstance(list: TaskList) : TaskDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(ARG_LIST, list)
            val fragment = TaskDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}