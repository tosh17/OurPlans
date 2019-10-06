package ru.thstdio.study.ourplans.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.thstdio.study.ourplans.MainActivity
import ru.thstdio.study.ourplans.di.module.FireBaseModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FireBaseModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    @Component.Builder
    interface MyBuilder {
        fun build(): AppComponent

        @BindsInstance
        fun setContext(applicationContext: Context): MyBuilder
    }
}