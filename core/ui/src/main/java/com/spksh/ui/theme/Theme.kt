package com.spksh.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

data class mainScheme(
    val primaryLight: Color,
    val primaryDark: Color,
    val secondaryLight: Color,
    val secondaryDark: Color
)

val mainSchemesList = listOf(
    mainScheme(
        primaryLight = Color(0xFF2AE881),
        primaryDark = Color(0xFF035034),
        secondaryLight = Color(0xFFD4FAE6),
        secondaryDark = Color(0xFF01251D),
    ),
    mainScheme(
        primaryLight = Color(0xFF2A86E8),
        primaryDark = Color(0xFF032C50),
        secondaryLight = Color(0xFFD4EFFA),
        secondaryDark = Color(0xFF011A25),
    ),
    mainScheme(
        primaryLight = Color(0xFFE8932A),
        primaryDark = Color(0xFF502D03),
        secondaryLight = Color(0xFFFAE9D4),
        secondaryDark = Color(0xFF251401),
    ),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF035034),
    secondary = Color(0xFF01251D),
    tertiary = Color(0xFFD2D5D1),
    outline = Color(0xFFC4C9C4),
    outlineVariant = Color(0xFF58585D),
    surfaceContainerLowest = Color(0xFF1E1E1E),
    surfaceContainerLow = Color(0xFF262626)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2AE881),
    secondary = Color(0xFFD4FAE6),
    tertiary = Color(0xFF3C3C43),

    background = Color(0xFFFEF7FF),
    onBackground = Color(0xFF1D1B20),
    outline = Color(0xFF49454F),
    outlineVariant = Color(0xFFCAC4D0),
    surfaceContainerLowest = Color(0xFFF3EDF7),
    surfaceContainerLow = Color(0xFFECE6F0),
)

private fun getLightColorScheme(color: Int) : ColorScheme {
    val filteredColor = if (color != 0 && color != 1 && color != 2) {
        0
    } else {
        color
    }
    val mainScheme = mainSchemesList[filteredColor]
    return lightColorScheme(
        primary = mainScheme.primaryLight,
        secondary = mainScheme.secondaryLight,

        tertiary = Color(0xFF3C3C43),
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1D1B20),
        outline = Color(0xFF49454F),
        outlineVariant = Color(0xFFCAC4D0),
        surfaceContainerLowest = Color(0xFFF3EDF7),
        surfaceContainerLow = Color(0xFFECE6F0),
    )
}

private fun getDarkColorScheme(color: Int) : ColorScheme {
    val filteredColor = if (color != 0 && color != 1 && color != 2) {
        0
    } else {
        color
    }
    val mainScheme = mainSchemesList[filteredColor]
    return darkColorScheme(
        primary = mainScheme.primaryDark,
        secondary = mainScheme.secondaryDark,

        tertiary = Color(0xFFD2D5D1),
        outline = Color(0xFFC4C9C4),
        outlineVariant = Color(0xFF58585D),
        surfaceContainerLowest = Color(0xFF1E1E1E),
        surfaceContainerLow = Color(0xFF262626)
    )
}

@Composable
fun FinanceAppTheme(
    darkTheme: Boolean = false, //isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    color: Int = 0,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> getDarkColorScheme(color)
        else -> getLightColorScheme(color)
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}