package com.example.connectmusic

import android.app.Application
import com.example.connectmusic.data.AppContainer
import com.example.connectmusic.data.AppDataContainer

class ConnectMusicApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}