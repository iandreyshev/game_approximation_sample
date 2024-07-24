package ru.iandreyshev.gamesample.backend

data class Message(
    val type: Int = 0,
    val x: Float = 0f,
    val y: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
    val serverTime: Float = 0f
)
