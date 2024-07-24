package ru.iandreyshev.gamesample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.iandreyshev.gamesample.mobile.draw.GameView
import ru.iandreyshev.gamesample.mobile.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val gameView: GameView by lazy { findViewById(R.id.gameView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            viewModel.onInitGame()
        }

        viewModel.state
            .onEach { gameView.render(it) }
            .launchIn(lifecycleScope)
    }

}
