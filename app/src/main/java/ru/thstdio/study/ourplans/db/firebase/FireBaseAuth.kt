package ru.thstdio.study.ourplans.db.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.subjects.PublishSubject
import ru.thstdio.study.ourplans.R
import ru.thstdio.study.ourplans.db.repo.Repo.Companion.USER_STATUS_NOT_AUTH


class FireBaseAuth(val store: FireBaseCloud) {
    //Google Sign In Client
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    //Firebase Auth
    private lateinit var mAuth: FirebaseAuth
    var userAuthStatus = PublishSubject.create<Int>()

    fun init(context: Context) {
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

    }

    fun checkIsUserLogin() {
        mAuth.currentUser?.let {
            store.loadUser(it, userAuthStatus)
        } ?: let {
            userAuthStatus.onNext(USER_STATUS_NOT_AUTH)
        }
    }


    fun getSignInIntent() = mGoogleSignInClient.signInIntent
    fun onActivityResult(data: Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)
            account?.let { firebaseAuthWithGoogle(account) }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w("Login", "Google sign in failed", e)
            // ...
        }
    }

    @SuppressLint("CheckResult")
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    user?.let {
                        store.loadUser(user,userAuthStatus)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)

                }
            }
    }
}
