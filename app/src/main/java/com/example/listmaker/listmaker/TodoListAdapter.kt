package com.example.listmaker.listmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(private val lists: ArrayList<TaskList>, private val clickListener: TodoListClickListener) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    interface TodoListClickListener {
        fun listItemClicked(list: TaskList){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        (position + 1).toString().also { holder.itemNumber.text = it }
        holder.itemString.text = lists[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount() = lists.size

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNumber : TextView = itemView.findViewById(R.id.itemNumber)
        val itemString : TextView = itemView.findViewById(R.id.itemString)
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }
}