package com.lewis.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerLinearAdapter(val context: Context, val todolist: List<list>): RecyclerView.Adapter<RecyclerLinearAdapter.ListHolder>(){
    override fun onBindViewHolder(holder: RecyclerLinearAdapter.ListHolder, position: Int) {
       holder.bind(todolist[position])//To change body of created functions use File | Settings | File Templates.

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerLinearAdapter.ListHolder {
         //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent.context).inflate(R.layout.viewitemlayout, parent, false)
            v.setOnClickListener {

            }
        return ListHolder(v)
    }

    override fun getItemCount(): Int {
        return  todolist.count()
    }

    inner class ListHolder(v: View): RecyclerView.ViewHolder(v) {
        private val title = v.findViewById<TextView>(R.id.mainTitleView)
        fun bind (mainlist: list){
            title.text = mainlist.title
        }

    }

}