package ru.iandreyshev.gamesample.mobile.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

data class BallDrawable(
    var x: Float = 0f,
    var y: Float = 0f,
    var size: Float = 0f
) : GameView.IDrawable {

    private val radius
        get() = size / 2
    private val xCenter
        get() = x + radius
    private val yCenter
        get() = y + radius

    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun drawOn(canvas: Canvas) {
        println("BALL AT: $yCenter")
        canvas.drawCircle(xCenter, yCenter, radius, paint)
    }

}
