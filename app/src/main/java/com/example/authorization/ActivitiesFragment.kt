package com.example.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.authorization.auth.LoginActivity
import com.example.authorization.firestore.FirestoreClass
import com.example.authorization.models.Post
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ActivitiesFragment : Fragment() {
    private lateinit var btn: Button
    private lateinit var logout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = view.findViewById(R.id.addPostBtn)
        logout = view.findViewById(R.id.logOutBtn)

        btn.setOnClickListener {
            changeActivity()
        }

        logout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra("finish", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        //activity!!.startActivity(intent)
        activity?.finish()
    }

    private fun changeActivity() {
        val intent = Intent(activity, AddPostActivity::class.java)
        startActivity(intent)
    }
}