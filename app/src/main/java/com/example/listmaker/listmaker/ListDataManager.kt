package com.example.listmaker.listmaker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class ListDataManager(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    fun saveLists(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
            .apply()
    }

    fun readLists() : ArrayList<TaskList> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPrefs.all
        val taskLists = ArrayList<TaskList>()

        for (content in contents) {
            val values = ArrayList(content.value as HashSet<String>)
            val list = TaskList(content.key, values)
            taskLists.add(list)
        }
        return taskLists
    }

    fun deleteLists(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.remove(list.name).apply()
    }
}