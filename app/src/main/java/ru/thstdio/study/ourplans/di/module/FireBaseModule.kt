package ru.thstdio.study.ourplans.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.thstdio.study.ourplans.db.firebase.FireBaseAuth
import ru.thstdio.study.ourplans.db.firebase.FireBaseCloud
import javax.inject.Singleton

@Module
class FireBaseModule {
    @Provides
    fun createFireBaseAuth(context: Context,store:FireBaseCloud): FireBaseAuth = FireBaseAuth(store).apply { init(context) }
    @Singleton
    @Provides
    fun createFireStore(): FireBaseCloud = FireBaseCloud()
}