package ru.iandreyshev.gamesample.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import ru.iandreyshev.gamesample.mobile.draw.GameView

class MainViewModel : ViewModel() {

    // Аналог LiveData, замените на LiveData
    val state = MutableStateFlow<List<GameView.IDrawable>>(emptyList())

    private val multiplayerGameplay = MultiplayerGameplay(
        scope = viewModelScope,
        onDraw = { state.value = it }
    )

    fun onInitGame() {
        multiplayerGameplay.start()
    }

}
