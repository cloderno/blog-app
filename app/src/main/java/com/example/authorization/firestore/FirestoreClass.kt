package com.example.authorization.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.authorization.AddPostActivity
import com.example.authorization.Constants
import com.example.authorization.adapter.PostsAdapter
import com.example.authorization.auth.SignUpActivity
import com.example.authorization.models.Post
import com.example.authorization.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.ArrayList

class FirestoreClass {
    private val fireStore = FirebaseFirestore.getInstance()

    // registers users in the collection in firebase database
    fun registerUser(activity: SignUpActivity, userInfo: User) {
        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
    }

    fun getCurrentUserId(): String {
        val curUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if (currentUserId != null) {
            currentUserId = curUser!!.uid
        }

        return currentUserId
    }

    // gets user details from the collection
    fun getCurrentUser(activity: Activity) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)

                //saves data on local android phone (localstorage)
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.POSTS,
                    Context.MODE_PRIVATE
                )

                //saving info about user to localstorage
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    //"${user!!.id}${user!!.email}"
                    "${user!!.id}"
                )
                editor.apply()

                editor.putString(
                    Constants.LOGGED_IN_EMAIL,
                    //"${user!!.id}${user!!.email}"
                    "${user!!.email}"
                )
                editor.apply()
            }
            .addOnFailureListener { it ->
                Log.i(activity.javaClass.simpleName, it.toString())
            }
    }

    // registers post in the collection in firebase database
    fun registerPost(activity: AddPostActivity, postInfo: Post) {
        fireStore.collection(Constants.POST)
            .document(postInfo.id)
            .set(postInfo, SetOptions.merge())
    }

}