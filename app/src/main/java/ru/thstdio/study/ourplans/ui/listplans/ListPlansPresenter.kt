package ru.thstdio.study.ourplans.ui.listplans

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.thstdio.study.ourplans.db.repo.entity.Plans
import ru.thstdio.study.ourplans.ui.listplans.ui.ListPlansAdapter
import ru.thstdio.study.ourplans.ui.listplans.ui.ListPlansHolderView
import ru.thstdio.study.ourplans.ui.listplans.ui.ListPlansView

@InjectViewState
class ListPlansPresenter : MvpPresenter<ListPlansView>() {
    lateinit var listPlans :List<Plans>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        var model=ListPlansModel()
        model.listPlans.subscribe{
            listPlans=it
            viewState.setAdapter()
        }
        model.loadPersonList()
    }



    fun onBind(holder: ListPlansHolderView, position: Int) {
        holder.setWorkTitle(listPlans[position].name)
    }

    fun getWorkListSize() = listPlans.size


    fun getItemViewType(position: Int): Int {
        return ListPlansAdapter.ViEW_WORK
    }
}