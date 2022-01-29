package com.example.authorization.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.authorization.EditPost
import com.example.authorization.FavouritesFragment
import com.example.authorization.R
import com.example.authorization.auth.LoginActivity
import com.example.authorization.models.Post

class PostAdapter(private var mContext: Context, private val postsList: ArrayList<Post>, private val listener: FavouritesFragment) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private lateinit var currentItem: Post
    private lateinit var intent: Intent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_single_post, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentItem = postsList[position]

        holder.author.text = currentItem.author
        holder.desc.text = currentItem.description
        holder.title.text = currentItem.title
        holder.date.text = currentItem.date.toString()
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.postTitle2)
        val desc: TextView = itemView.findViewById(R.id.postDescription2)
        val author: TextView = itemView.findViewById(R.id.postAuthor2)
        val date: TextView = itemView.findViewById(R.id.postDate2)

        init {
            itemView.findViewById<Button>(R.id.deletePost2).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }

            itemView.findViewById<Button>(R.id.editPost).setOnClickListener {
                var curItem = postsList[adapterPosition]
                intent = Intent(mContext, EditPost::class.java)

                intent.putExtra("id", "${curItem.id}")
                intent.putExtra("title", "${title.text}")
                intent.putExtra("desc", "${desc.text}")
                intent.putExtra("author", "${author.text}")
                intent.putExtra("date", "${date.text}")

                Toast.makeText(mContext, "ID: ${curItem.id}", Toast.LENGTH_SHORT).show()
                Toast.makeText(mContext, "txt: ${title.text}", Toast.LENGTH_SHORT).show()
                mContext!!.startActivity(intent)

            }
        }


        override fun onClick(v: View?) {
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}