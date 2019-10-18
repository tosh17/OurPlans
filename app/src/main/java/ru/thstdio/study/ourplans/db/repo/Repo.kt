package ru.thstdio.study.ourplans.db.repo

import ru.thstdio.study.ourplans.db.repo.entity.User

class Repo {
    companion object{
        const val USER_STATUS_NOT_AUTH=0
        const val USER_STATUS_CREATE=1
        const val USER_LOAD=2
    }
    lateinit var user: User




}