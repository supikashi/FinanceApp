package com.spksh.financeapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.spksh.financeapp.background.WorkScheduler
import com.spksh.financeapp.ui.screen.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installCustomSplash()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        WorkScheduler.scheduleDataFetch(applicationContext)
        setContent {
            MainScreen(
                viewModelFactory = remember {
                    appComponent.viewModelProviderFactory()
                },
                appComponent = appComponent
            )
        }
    }

    private fun installCustomSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.9f,
                    0.01f
                )
                zoomX.duration = 300L
                zoomX.doOnEnd { screen.remove() }
                zoomX.start()
            }
        }
    }
}