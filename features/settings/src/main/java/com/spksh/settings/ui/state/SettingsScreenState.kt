package com.spksh.settings.ui.state

data class SettingsScreenState(
    val currentLanguage: String,
    val syncTime: String?,
    val isDarkTheme: Boolean,
    val color: Int
)