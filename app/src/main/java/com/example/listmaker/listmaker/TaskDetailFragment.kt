package com.example.listmaker.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.listmaker.listmaker.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding

    private lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getParcelable(ARG_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
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