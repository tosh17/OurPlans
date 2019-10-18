package ru.thstdio.study.ourplans.ui.listplans.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.study.ourplans.R
import ru.thstdio.study.ourplans.ui.listplans.ListPlansPresenter

class ListPlansAdapter(val presenter: ListPlansPresenter) :
    RecyclerView.Adapter<ListPlansHolder>() {
    companion object {
        const val ViEW_WORK = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlansHolder {
        return when (viewType) {
            ViEW_WORK -> ListPlansHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_plan_rv_item,
                    parent,
                    false
                ), presenter
            )
            else -> ListPlansHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_plan_rv_item,
                    parent,
                    false
                ), presenter
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return presenter.getItemViewType(position)
    }

    override fun getItemCount(): Int = presenter.getWorkListSize()

    override fun onBindViewHolder(holder: ListPlansHolder, position: Int) {
        presenter.onBind(holder, position)
    }

}