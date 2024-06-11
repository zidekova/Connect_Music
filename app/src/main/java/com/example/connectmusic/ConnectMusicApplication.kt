package com.example.connectmusic

import android.app.Application
import com.example.connectmusic.data.AppContainer
import com.example.connectmusic.data.AppDataContainer
import com.example.connectmusic.data.DatabaseInitializer

class ConnectMusicApplication : Application() {
    /**
     * Instancia AppContaineru na ziskanie zavislosti
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        DatabaseInitializer.initialize(this)
        container = AppDataContainer(this)
    }
}