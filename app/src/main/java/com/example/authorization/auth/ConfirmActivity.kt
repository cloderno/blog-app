package com.example.authorization.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.authorization.R

class ConfirmActivity : AppCompatActivity() {
    private var regMail: TextView = findViewById(R.id.regMail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        var activity = SignUpActivity()
        //var email = activity.getEmailInput()

        //regMail.setText(email)
    }
}