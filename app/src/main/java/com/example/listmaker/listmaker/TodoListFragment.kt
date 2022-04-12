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
            listDataManager = ListDataManager(it)
        }

        val lists = listDataManager.readLists()  //listDataManager has to be initialized before this line, as it is above, to remove not initialized error
        todoListRecyclerView = view.findViewById(R.id.rvTodoList)
        todoListRecyclerView.layoutManager = LinearLayoutManager(context)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)

        binding.fabCreate.setOnClickListener {
            showCreateTodoListDialog()
        }
    }

    companion object {
        fun newInstance() : TodoListFragment {
            return TodoListFragment()
        }
    }

    interface OnFragmentInteractionListener {
        fun onTodoListClicked (list: TaskList)
    }

    private fun addList(list: TaskList) {
        listDataManager.saveLists(list)
        val adapter = todoListRecyclerView.adapter as TodoListAdapter
        adapter.addList(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveLists(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
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

    private fun showTaskListItems(list: TaskList) {

    }
}