package com.example.listmaker.listmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter() : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    private var todoLists = mutableListOf("Android development", "House Work", "Errands", "Shopping")

    fun addNewItem(listItem: String) {
        if (listItem.isEmpty()) {
            todoLists.add("Todo list " + (todoLists.size + 1))
        } else {
            todoLists.add(listItem)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.itemNumber.text = (position + 1).toString()
        holder.itemString.text = todoLists[position]
    }

    override fun getItemCount() = todoLists.size

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNumber : TextView = itemView.findViewById(R.id.itemNumber)
        val itemString : TextView = itemView.findViewById(R.id.itemString)
    }
}