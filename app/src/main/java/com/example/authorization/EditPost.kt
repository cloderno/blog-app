package com.example.authorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import java.util.*

class EditPost : AppCompatActivity() {
    private var id: String? = null
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var author: String
    private lateinit var date: Date
    private lateinit var titleField: EditText
    private lateinit var descriptionField: EditText
    private lateinit var updateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        titleField = findViewById(R.id.postTitleText2)
        descriptionField = findViewById(R.id.postDescriptionText2)
        updateBtn = findViewById(R.id.updatePost)

        getIncomingIntent()

        updateBtn.setOnClickListener {
            updatePost()
        }
    }

    private fun getIncomingIntent() {
        id = intent.getStringExtra("id").toString()
        title = intent.getStringExtra("title").toString()
        description = intent.getStringExtra("desc").toString()
        author = intent.getStringExtra("author").toString()
        date = Calendar.getInstance().time

        titleField.setText(title)
        descriptionField.setText(description)
    }

    private fun updatePost() {
        var map = HashMap<String, Any>()
        map.put("id", intent.getStringExtra("id").toString())
        map.put("author", intent.getStringExtra("author").toString())
        map.put("title", titleField.text.toString())
        map.put("description", descriptionField.text.toString())
        map.put("date", Calendar.getInstance().time)

        var db = FirebaseFirestore.getInstance()
        db.collection("post")
            .document(intent.getStringExtra("id").toString())
            .update(map)

        Toast.makeText(this, "Post has been updated.", Toast.LENGTH_SHORT).show()
        map.clear()
        onBackPressed()
    }

}