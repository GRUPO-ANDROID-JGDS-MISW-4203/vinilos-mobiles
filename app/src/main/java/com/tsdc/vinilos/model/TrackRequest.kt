package com.tsdc.vinilos.model

data class TrackRequest(
    val name: String,
    val duration: String,
    val videoUrl: String = ""
)