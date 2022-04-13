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
import androidx.recyclerview.widget.ItemTouchHelper
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

        val lists = listDataManager.readLists()
        todoListRecyclerView = view.findViewById(R.id.rvTodoList)
        todoListRecyclerView.layoutManager = LinearLayoutManager(context)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        activity?.let {
            val swipeHandler = object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val todoListAdapter = todoListRecyclerView.adapter as TodoListAdapter
                    val position = viewHolder.adapterPosition
                    val lists = listDataManager.readLists()
                    val list = lists[position]

                    todoListAdapter.removeListAt(position)
                    listDataManager.deleteLists(list)
                    updateLists()
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(todoListRecyclerView)
        }

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

    private fun updateLists() {
        val lists = listDataManager.readLists()
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }
}