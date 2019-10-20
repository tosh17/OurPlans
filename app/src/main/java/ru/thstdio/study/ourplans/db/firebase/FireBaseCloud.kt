package ru.thstdio.study.ourplans.db.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.subjects.PublishSubject
import ru.thstdio.study.ourplans.db.repo.Repo
import ru.thstdio.study.ourplans.db.repo.Repo.Companion.USER_LOAD
import ru.thstdio.study.ourplans.db.repo.Repo.Companion.USER_STATUS_CREATE
import ru.thstdio.study.ourplans.db.repo.entity.Plans
import ru.thstdio.study.ourplans.db.repo.entity.User

class FireBaseCloud(val repo: Repo) {


    private val db: FirebaseFirestore

    init {
        db = FirebaseFirestore.getInstance()
    }

    fun createUser(
        firebaseUser: FirebaseUser,
        userAuthStatus: PublishSubject<Int>
    ) {
        val user = User(
            firebaseUser.uid,
            if (firebaseUser.displayName != null) {
                firebaseUser.displayName!!
            } else {
                ""
            }, "", firebaseUser.photoUrl.toString()
        )
        repo.user = user!!
        db.collection("users").document(firebaseUser.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                userAuthStatus.onNext(USER_STATUS_CREATE)
            }
            .addOnFailureListener { e ->
                //    Log.w(TAG, "Error adding document", e)
            }

    }


    fun loadUser(
        userFb: FirebaseUser,
        userAuthStatus: PublishSubject<Int>
    ) {
        db.collection("users")
            .document(userFb.uid).get()
            .addOnSuccessListener { userFbStore ->
                if (userFbStore != null) {
                    val user = userFbStore.toObject(User::class.java)
                    repo.user = user!!
                    userAuthStatus.onNext(USER_LOAD)
                } else {
                    createUser(userFb, userAuthStatus)
                }
            }
            .addOnFailureListener { exception ->

            }
    }
    fun getCollection(category:String)=when(category){
        "users"-> db.collection("users")
        "active"-> db.collection("personalplan").document(repo.user.id).collection("active")
        else -> throw Error("No Collection")
    }
    fun loadPersonList(listPlans: PublishSubject<List<Plans>>) {
        val docRef =  getCollection("active")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("TEST", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val plans = ArrayList<Plans>()
                for (doc in value!!) {
                    plans.add(doc.toObject(Plans::class.java))
                }
                listPlans.onNext(plans)
            }

    }

    fun createPersonalPlans(plan:Plans){
          getCollection("active").document(plan.id)
            .set(plan)
            .addOnSuccessListener { documentReference ->
               }
            .addOnFailureListener { e ->
                //    Log.w(TAG, "Error adding document", e)
            }

    }
}