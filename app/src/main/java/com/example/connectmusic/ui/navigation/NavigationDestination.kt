package com.example.connectmusic.ui.navigation

/**
 * Rozhranie pre vsetky navigacne destinacie.
 * Uchovava nazov cesty k destinacii a id k String resource pre titulok na danej obrazovke.
 */
interface NavigationDestination {
    val route: String
    val titleRes: Int
}