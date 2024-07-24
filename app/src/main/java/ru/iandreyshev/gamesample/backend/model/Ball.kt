package ru.iandreyshev.gamesample.backend.model

data class Ball(
    var x: Float = 500f,
    var y: Float = 500f,
    var speed: Float = 0f
) {

    val top = y
    val bottom = y + SIZE
    val left = x
    val right = x + SIZE

    companion object {
        const val SIZE = 100f
        const val GRAVITY = 250f
    }

}
