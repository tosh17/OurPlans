package ru.thstdio.study.ourplans.ui.listplans
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.thstdio.study.ourplans.db.firebase.FireBaseCloud
import ru.thstdio.study.ourplans.db.repo.entity.Plans

class ListPlansModel : KoinComponent {

    val firebaseBd: FireBaseCloud by inject()

    var listPlans = PublishSubject.create<List<Plans>>()

    fun loadPersonList(){
        firebaseBd.loadPersonList(listPlans)
    }




}