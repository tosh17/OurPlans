package ru.thstdio.study.ourplans.ui.listplans.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ListPlansView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setAdapter()
}