package ru.thstdio.study.ourplans.db.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import ru.thstdio.study.ourplans.db.repo.User

class FireBaseCloud {

    private val db: FirebaseFirestore

    init {
        db = FirebaseFirestore.getInstance()
    }

    fun createUser(firebaseUser: FirebaseUser) {
        val user = User(
            firebaseUser.uid,
            if (firebaseUser.displayName != null) {
                firebaseUser.displayName!!
            } else {
                ""
            }, "", firebaseUser.photoUrl.toString()
        )
        db.collection("users").document(firebaseUser.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
              //  Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
            //    Log.w(TAG, "Error adding document", e)
            }
    }

}