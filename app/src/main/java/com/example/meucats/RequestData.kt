package com.example.meucats

data class RequestData(
    val breeds: List<String>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)