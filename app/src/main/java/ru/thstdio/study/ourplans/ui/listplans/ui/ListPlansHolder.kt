package ru.thstdio.study.ourplans.ui.listplans.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_plan_rv_item.view.*
import ru.thstdio.study.ourplans.ui.listplans.ListPlansPresenter

class ListPlansHolder(itemView: View, presenter: ListPlansPresenter) :
    RecyclerView.ViewHolder(itemView), ListPlansHolderView {
    override fun setWorkTitle(title: String) {
        itemView.textView_title.text = title
    }


}
