package com.example.authorization

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.authorization.adapter.PostAdapter
import com.example.authorization.adapter.PostsAdapter
import com.example.authorization.models.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FavouritesFragment : Fragment(), PostsAdapter.OnItemClickListener  {
    private lateinit var postRecyclerView: RecyclerView
    private lateinit var postsArrayList: ArrayList<Post>
    private lateinit var adapter: PostAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var clickedItem: Post
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = FirebaseAuth.getInstance().currentUser!!.email.toString()

        // rec view
        postRecyclerView = view.findViewById(R.id.postList)
        postRecyclerView.layoutManager = LinearLayoutManager(context)
        postRecyclerView.setHasFixedSize(true)

        postsArrayList = arrayListOf<Post>()
        getPosts()
        //getIt()

        // swipe refresh
        swipeRefresh = view.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            getPosts()

            swipeRefresh.isRefreshing = false
        }
    }

    //fetch posts from firestore
    private fun getPosts() {
        val db = FirebaseFirestore.getInstance()
        db.collection("post")
            .get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    if(p0.isSuccessful){
                        postsArrayList.isNotEmpty() && postsArrayList.removeAll(postsArrayList)
                        for(data in p0.result) {
                            // changing description string
//                            var desc: String = (data.get("description") as String).take(35).plus("..")
                            var email = FirebaseAuth.getInstance().currentUser!!.email

                            if (email == data.get("author")) {
                                // adding data to an array
                                postsArrayList.add(
                                    Post(
                                        data.get("id") as String,
                                        data.get("author") as String,
                                        data.get("title") as String,
                                        data.get("description") as String,
                                        data.getTimestamp("date")!!.toDate(),
                                    ))
                            }
                        }
                        adapter = PostAdapter(requireContext(), postsArrayList, this@FavouritesFragment)
                        postRecyclerView.adapter = adapter
                    }
                }
            })
    }

    //delete item from firebase
    override fun onItemClick(position: Int) {
        clickedItem = postsArrayList[position]

        var db = FirebaseFirestore.getInstance()
        db.collection("post")
            .document(clickedItem.id)
            .delete()

        Toast.makeText(requireContext(), "Post has been deleted.", Toast.LENGTH_SHORT).show()
        getPosts()
    }
}