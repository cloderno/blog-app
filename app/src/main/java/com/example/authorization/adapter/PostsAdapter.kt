package com.example.authorization.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.authorization.R
import com.example.authorization.models.Post
import com.example.authorization.HomeFragment

class PostsAdapter(private val postsList: ArrayList<Post>, private val listener: HomeFragment) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_post, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = postsList[position]

        holder.author.text = currentItem.author
        holder.desc.text = currentItem.description
        holder.title.text = currentItem.title
        holder.date.text = currentItem.date.toString()
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.postTitle)
        val desc: TextView = itemView.findViewById(R.id.postDescription)
        val author: TextView = itemView.findViewById(R.id.postAuthor)
        val date: TextView = itemView.findViewById(R.id.postDate)

        init {

        }


        override fun onClick(v: View?) {
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}