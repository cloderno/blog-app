package com.example.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.authorization.adapter.PostsAdapter
import com.example.authorization.models.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), PostsAdapter.OnItemClickListener {
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postsArrayList: ArrayList<Post>
    private lateinit var adapter: PostsAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var clickedItem: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // rec view
        postsRecyclerView = view.findViewById(R.id.postsList)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)
        postsRecyclerView.setHasFixedSize(true)

        postsArrayList = arrayListOf<Post>()
        getPosts()

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
            .addOnCompleteListener(object :OnCompleteListener<QuerySnapshot>{
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    if(p0.isSuccessful){
                        postsArrayList.isNotEmpty() && postsArrayList.removeAll(postsArrayList)
                        for(data in p0.result) {
                            // changing description string
//                            var desc: String = (data.get("description") as String).take(35).plus("..")

                            // adding data to an array
                            postsArrayList.add(Post(
                                data.get("id") as String,
                                data.get("author") as String,
                                data.get("title") as String,
                                data.get("description") as String,
                                data.getTimestamp("date")!!.toDate(),
                            ))
                        }
                        adapter = PostsAdapter(postsArrayList, this@HomeFragment )
                        postsRecyclerView.adapter = adapter
                    }
                }
            })
    }

    private fun saveToFavourites(id: String, position: Int) {
        postsArrayList.get(position)
        Toast.makeText(context, "${id}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onItemDelete(position: Int) {
//        clickedItem = postsArrayList[position]
//        Toast.makeText(context, "DELETED ${clickedItem.id}", Toast.LENGTH_SHORT).show()
//
//        val db = FirebaseFirestore.getInstance()
//        db.collection("post")
//            .document(clickedItem.id)
//            .delete()
//    }

//    private fun cutString(toCut: String): String {
//        if (toCut.length > 35) {
//            return toCut.take(35).plus("..")
//        }
//        return toCut
//    }
}