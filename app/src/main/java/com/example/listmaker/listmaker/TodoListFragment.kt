package com.example.listmaker.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.listmaker.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var binding: FragmentTodoListBinding
    private var listener: OnFragmentInteractionListener? = null

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
        val lists = listDataManager.readLists()
        todoListRecyclerView = view.findViewById(R.id.rvTodoList)
        todoListRecyclerView.layoutManager = LinearLayoutManager(context)
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
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

    fun addList(list: TaskList) {
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

    override fun listItemClicked(list: TaskList) {
        listener?.onTodoListClicked(list)
    }
}