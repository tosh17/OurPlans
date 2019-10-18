package ru.thstdio.study.ourplans.di.koin

import org.koin.dsl.module
import ru.thstdio.study.ourplans.db.firebase.FireBaseAuth
import ru.thstdio.study.ourplans.db.firebase.FireBaseCloud

val firebaseModule = module {
    factory { FireBaseAuth(get()).apply { init(get()) } }
    single { FireBaseCloud(get()) }
}