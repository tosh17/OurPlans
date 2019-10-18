package ru.thstdio.study.ourplans.ui.listplans.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_plan_fragment.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.thstdio.study.ourplans.R
import ru.thstdio.study.ourplans.ui.listplans.ListPlansPresenter

class ListPlansFragment : MvpAppCompatFragment(), ListPlansView {

    @InjectPresenter
    internal lateinit var presenter: ListPlansPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_plan_fragment, null)

    }

    override fun setAdapter() {
        view?.let {
            if(it.plant_list.adapter==null){
                val adapter = ListPlansAdapter(presenter)
                it.plant_list.adapter = adapter
            }
            it.plant_list.adapter?.run{
                notifyDataSetChanged()
            }

        }
    }

}