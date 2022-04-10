package com.example.listmaker.listmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(private var list: TaskList) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.taskName.text = list.tasks[position]
    }

    override fun getItemCount() = list.tasks.size

    inner class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName : TextView = itemView.findViewById(R.id.taskName)
    }
}