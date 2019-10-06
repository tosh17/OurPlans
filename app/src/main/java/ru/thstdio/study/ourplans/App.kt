package ru.thstdio.study.ourplans

import android.app.Application
import ru.thstdio.study.ourplans.di.component.AppComponent
import ru.thstdio.study.ourplans.di.component.DaggerAppComponent

class App: Application() {

    companion object {
        lateinit var daggerComponent: AppComponent private set

    }

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder().setContext(this).build()
    }
}