package ru.iandreyshev.gamesample.backend.model

data class Platform(
    val x: Float = 350f,
    val y: Float = 1200f,
    val width: Float = 300f,
    val height: Float = 100f
) {

    val top = y
    val bottom = y + height
    val left = x
    val right = x + width
}
