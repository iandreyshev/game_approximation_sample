package ru.iandreyshev.gamesample.mobile

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.iandreyshev.gamesample.backend.Backend
import ru.iandreyshev.gamesample.mobile.draw.GameView


class MultiplayerGameplay(
    private val scope: CoroutineScope,
    private val onDraw: suspend (List<GameView.IDrawable>) -> Unit
) {

    private val dataCache = DataCache()
    private val backend = Backend(scope)

    fun start() {
        connectToServer()
        runLocalLoop()
    }

    private fun connectToServer() {
        backend.connect { messages ->
            dataCache.submitNewData(messages)
        }
    }

    private fun runLocalLoop() {
        scope.launch {
            var lastFrameTime = System.currentTimeMillis()

            while (true) {
                val systemTime = System.currentTimeMillis()
                val elapsedTime = (systemTime - lastFrameTime) / 1000f

                if (elapsedTime < ONE_FRAME_TIME) {
                    delay(1)
                    continue
                }

                println("Draw frame")

                val objectsToDraw = dataCache.getDrawables(elapsedTime)
                onDraw(objectsToDraw)

                lastFrameTime = systemTime
            }
        }
    }

    companion object {
        private const val ONE_FRAME_TIME = 1 / 60f
    }

}
