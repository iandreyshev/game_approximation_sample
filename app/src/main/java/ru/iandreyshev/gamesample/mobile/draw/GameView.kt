package ru.iandreyshev.gamesample.mobile.draw

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View


class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    interface IDrawable {
        fun drawOn(canvas: Canvas)
    }

    private var state: List<IDrawable> = emptyList()

    fun render(state: List<IDrawable>) {
        this.state = state
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        state.forEach { it.drawOn(canvas) }
    }

}
