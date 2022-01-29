package com.example.authorization.models

import java.util.*

class Post(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val date: Date = Calendar.getInstance().time,
        )