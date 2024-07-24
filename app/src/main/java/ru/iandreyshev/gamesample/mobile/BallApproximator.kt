package ru.iandreyshev.gamesample.mobile

import ru.iandreyshev.gamesample.backend.Message
import ru.iandreyshev.gamesample.mobile.draw.BallDrawable
import ru.iandreyshev.gamesample.mobile.draw.GameView
import ru.iandreyshev.gamesample.mobile.draw.PlatformDrawable

class BallApproximator {

    private var lastBall = BallDrawable()
    private var previousBall = BallDrawable()

    private var lastTime = 0f
    private var previousTime = 0f

    private var ballToDraw = BallDrawable()
    private var platformToDraw = PlatformDrawable()

    private var timeLag = 0f
    private var elapsedTime = 0f

    fun submitNewData(messages: List<Message>) {
        elapsedTime = 0f

        previousBall = lastBall
        ballToDraw = previousBall
        previousTime = lastTime

        messages.forEach {
            when (it.type) {
                0 -> {
                    lastBall = BallDrawable(
                        x = it.x,
                        y = it.y,
                        size = it.width
                    )
                }

                1 -> {
                    platformToDraw = PlatformDrawable(
                        x = it.x,
                        y = it.y,
                        widht = it.width,
                        height = it.height
                    )
                }

                2 -> {
                    lastTime = it.serverTime
                }
            }
        }

        timeLag = lastTime - previousTime
    }

    fun getDrawables(elapsedTime: Float): List<GameView.IDrawable> {
        this.elapsedTime += elapsedTime

        val movingPercent = (this.elapsedTime / timeLag).coerceAtMost(1f)
        val move = (lastBall.y - previousBall.y)
        val newY = move * movingPercent

        ballToDraw.y += newY

        return listOf(ballToDraw.copy(), platformToDraw)
    }

}
