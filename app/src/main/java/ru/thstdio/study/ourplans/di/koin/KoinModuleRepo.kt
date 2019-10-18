package ru.thstdio.study.ourplans.di.koin

import org.koin.dsl.module
import ru.thstdio.study.ourplans.db.repo.Repo

val repoModule = module {
    single { Repo() }
}