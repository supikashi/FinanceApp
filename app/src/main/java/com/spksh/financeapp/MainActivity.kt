package com.spksh.financeapp

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spksh.financeapp.background.WorkScheduler
import com.spksh.financeapp.ui.screen.MainScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale


class MainActivity : ComponentActivity() {

    private var isDarkTheme = false

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(applySelectedAppLanguage(base))
    }

    private fun applySelectedAppLanguage(context: Context): Context {
        val language = runBlocking { context.dataStoreRepository.getAppLanguage().first() }
        isDarkTheme = runBlocking { context.dataStoreRepository.getThemeMode().first() }
        val locale = when (language) {
            "en" -> Locale.ENGLISH
            "ru" -> Locale("ru")
            else -> Locale.getDefault()
        }
        val newConfig = Configuration(context.resources.configuration)
        newConfig.uiMode = if (isDarkTheme) {
            Configuration.UI_MODE_NIGHT_YES
        } else {
            Configuration.UI_MODE_NIGHT_NO
        }
        newConfig.setLocale(locale)
        return context.createConfigurationContext(newConfig)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        installCustomSplash()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        WorkScheduler.scheduleDataFetch(applicationContext)
        setContent {
            val color by dataStoreRepository.getMainColor().collectAsStateWithLifecycle(0)
            MainScreen(
                viewModelFactory = remember {
                    appComponent.viewModelProviderFactory()
                },
                appComponent = appComponent,
                isDarkTheme = isDarkTheme,
                color = color
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