package com.kunalmadan.github.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kunalmadan.github.R
import com.kunalmadan.github.network.Repository

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var data = listOf<Repository>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name: TextView = itemView.findViewById(R.id.name)
        val fullName: TextView = itemView.findViewById(R.id.full_name)

        fun bind(item: Repository) {
            name.text = item.name
            fullName.text = item.full_name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.repository_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }
}