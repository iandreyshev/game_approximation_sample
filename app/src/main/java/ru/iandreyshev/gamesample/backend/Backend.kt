package ru.iandreyshev.gamesample.backend

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.iandreyshev.gamesample.backend.model.Ball
import ru.iandreyshev.gamesample.backend.model.Platform

class Backend(
    private val coroutineScope: CoroutineScope
) {

    private val messages = MutableSharedFlow<List<Message>>()
    private var serverTime: Float = 0f
    private val ball = Ball()
    private val platform = Platform()

    fun connect(messages: (List<Message>) -> Unit) {
        this.messages
            .onEach { messages(it) }
            .launchIn(coroutineScope)

        startServerLoop()
    }

    private fun startServerLoop() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                var lastFrameTime = System.currentTimeMillis()
                var ticksCount = 0

                while (true) {
                    val systemTime = System.currentTimeMillis()
                    val elapsedTime = (systemTime - lastFrameTime) / 1000f

                    if (elapsedTime < ONE_TICK_TIME) {
                        delay(1)
                        continue
                    }

                    processPhysics(elapsedTime)

                    serverTime += elapsedTime

                    if (ticksCount == TICKS_TO_SEND) {
                        ticksCount = 0
                        sendMessage()
                    }

                    ticksCount++
                    lastFrameTime = systemTime
                }
            }
        }
    }

    private fun processPhysics(elapsedTime: Float) {
        val newY = ball.y + ball.speed * elapsedTime
        val isNotIntersect = newY + Ball.SIZE < platform.top // шар над платформой
                || newY > platform.bottom // мяч под платформой
                || ball.right < platform.left // мяч слева
                || ball.left > platform.right // мяч справа

        // Продолжаем падение
        if (isNotIntersect) {
            ball.y = newY
            ball.speed += Ball.GRAVITY * elapsedTime
            return
        }

        // Отбиться от платформы
        ball.y = platform.top - Ball.SIZE
        ball.speed = -ball.speed.toInt().toFloat()
    }

    private fun sendMessage() {
        coroutineScope.launch {
            messages.emit(
                listOf(
                    Message(0, ball.x, ball.y, Ball.SIZE),
                    Message(1, platform.x, platform.y, platform.width, platform.height),
                    Message(2, serverTime = serverTime)
                )
            )
        }
    }

    companion object {
        const val ONE_TICK_TIME = 1 / 60f
        const val TICKS_TO_SEND = 5
    }

}
