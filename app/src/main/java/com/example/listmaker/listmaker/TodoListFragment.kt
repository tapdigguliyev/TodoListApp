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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var listDataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
        }

        val lists = listDataManager.readLists()  //listDataManager has to be initialized before this line, as it is above, to remove not initialized error
        todoListRecyclerView = view.findViewById(R.id.rvTodoList)
        todoListRecyclerView.layoutManager = LinearLayoutManager(context)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        binding.fabCreate.setOnClickListener {
            showCreateTodoListDialog()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    private fun showCreateTodoListDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val todoTitleEditText = EditText(it)
            todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            builder.setTitle(getString(R.string.name_of_list))
            builder.setView(todoTitleEditText)
            builder.setPositiveButton(getString(R.string.create_list)) {
                    dialog, _ ->
                val list = TaskList(todoTitleEditText.text.toString())
                addList(list)
                dialog.dismiss()
                showTaskListItems(list)
            }
            builder.create().show()
        }
    }

    private fun addList(list: TaskList) {
        listDataManager.saveLists(list)
        val adapter = todoListRecyclerView.adapter as TodoListAdapter
        adapter.addList(list)
    }

    private fun showTaskListItems(list: TaskList) {
        view?.let {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTaskDetailFragment(list.name)
            it.findNavController().navigate(action)
        }
    }
}