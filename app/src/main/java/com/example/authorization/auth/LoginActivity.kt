package com.example.authorization.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.authorization.MainActivity
import com.example.authorization.R
import com.example.authorization.firestore.FirestoreClass
import com.example.authorization.models.User
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerViewButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //supportActionBar!!.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        emailInput = findViewById(R.id.emailLoginText)
        passwordInput = findViewById(R.id.passwordLoginText)
        loginBtn = findViewById(R.id.loginButton)
        registerViewButton = findViewById(R.id.registerViewButton)


        loginBtn.setOnClickListener {
            loginUser(emailInput.text.toString().trim(), passwordInput.text.toString().trim())
        }

        //navigates to the SignUp activity
        registerViewButton.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    //user registration
    private fun loginUser(email: String, password: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Logging in..")
        progressDialog.show()

        when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                signIn(email, password, progressDialog)
            }
        }
    }

    //populate user model's data
//    private fun populateUserData(): User {
//        var curUser = auth.currentUser
//        val user = User( curUser!!.uid, "323" )
//
//        return user
//    }

    //sign in function
    private fun signIn(email: String, password: String, progressDialog: ProgressDialog) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {  t ->
                FirestoreClass().getCurrentUser(this)

                progressDialog.dismiss()
                changeActivity()

                Toast.makeText(this, "Welcome ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun changeActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}