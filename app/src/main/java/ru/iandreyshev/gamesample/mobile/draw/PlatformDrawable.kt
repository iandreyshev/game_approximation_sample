package ru.iandreyshev.gamesample.mobile.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class PlatformDrawable(
    var x: Float = 0f,
    var y: Float = 0f,
    var widht: Float = 0f,
    var height: Float = 0f
) : GameView.IDrawable {

    private val paint = Paint().apply {
        color = Color.BLACK
    }

    override fun drawOn(canvas: Canvas) {
        canvas.drawRect(x, y, x + widht, y + height, paint)
    }

}
