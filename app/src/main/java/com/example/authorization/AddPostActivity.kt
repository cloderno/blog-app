package com.example.authorization

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.authorization.auth.LoginActivity
import com.example.authorization.firestore.FirestoreClass
import com.example.authorization.models.Post
import java.util.*

class AddPostActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var title: EditText
    private lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        btn = findViewById(R.id.addPost)

        title = findViewById<EditText>(R.id.postTitleText)
        description = findViewById<EditText>(R.id.postDescriptionText)
    }

    override fun onStart() {
        super.onStart()

        btn.setOnClickListener {
            //var user = User()
//            var id = user.getUserId()
//
            val sharedPreferences = getSharedPreferences(Constants.POSTS, Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")
            val userEmail = sharedPreferences.getString(Constants.LOGGED_IN_EMAIL, "")

            val id = UUID.randomUUID().toString()

            try {
                val post = Post("${id}", "${userEmail}", "${title.text.toString()}", "${description.text.toString()}")
                FirestoreClass().registerPost(this@AddPostActivity, post)

                changeActivity()

                Toast.makeText(this, "Post has been succesfully added", Toast.LENGTH_SHORT).show()
            }
            catch (e: Error) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("finish", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        finish()
    }
}