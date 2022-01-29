package com.example.authorization.auth

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.authorization.MainActivity
import com.example.authorization.R
import com.example.authorization.firestore.FirestoreClass
import com.example.authorization.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginViewButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //getting firebase auth instance
        auth = FirebaseAuth.getInstance()

        emailInput = findViewById(R.id.emailSignupText)
        passwordInput = findViewById(R.id.passwordSignupText)
        registerBtn = findViewById(R.id.registerBtn)
        loginViewButton = findViewById(R.id.loginViewBtn)


        //register button
        registerBtn.setOnClickListener {
//            var activity = Intent(this, ConfirmActivity::class.java)
//            startActivity(activity)
            registerUser(emailInput.text.toString().trim(), passwordInput.text.toString().trim())
        }

        //navigates back to the Login activity
        loginViewButton.setOnClickListener {
            onBackPressed()
        }
    }

    //user registration
    private fun registerUser(email: String, password: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Creating an account...")
        progressDialog.show()

        when {
            TextUtils.isEmpty(email) -> {
                progressDialog.dismiss()
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password) -> {
                progressDialog.dismiss()
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {

                        register(progressDialog)

                        signIn(email, password, progressDialog)
                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }


            }
        }
    }

    //register function
    private fun register(progressDialog: ProgressDialog) {
        val curUser = auth.currentUser
        val user = User( curUser!!.uid, curUser!!.email!! )

        FirestoreClass().registerUser(this@SignUpActivity, user)

        progressDialog.dismiss()

        Toast.makeText(this, "Account has been successfully created", Toast.LENGTH_SHORT).show()
    }

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

    //change activity
    private fun changeActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}