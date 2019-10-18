package ru.thstdio.study.ourplans

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.thstdio.study.ourplans.di.koin.firebaseModule
import ru.thstdio.study.ourplans.di.koin.repoModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            // modules
            modules(listOf(firebaseModule, repoModule))
        }

    }
}