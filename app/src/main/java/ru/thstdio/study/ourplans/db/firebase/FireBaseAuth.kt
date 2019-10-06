package ru.thstdio.study.ourplans.db.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ru.thstdio.study.ourplans.R

class FireBaseAuth(val store:FireBaseCloud) {
    //Google Sign In Client
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    //Firebase Auth
    private lateinit var mAuth: FirebaseAuth

    fun init(context: Context) {
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

    }

    fun getCurrentUser() = mAuth.currentUser
    fun getSignInIntent() = mGoogleSignInClient.signInIntent
    fun onActivityResult(data: Intent,upDateUI: (FirebaseUser) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)
            account?.let { firebaseAuthWithGoogle(account,upDateUI) }
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w("Login", "Google sign in failed", e)
            // ...
        }
    }

    private fun firebaseAuthWithGoogle(
        acct: GoogleSignInAccount,
        upDateUI: (FirebaseUser) -> Unit
    ) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    user?.let {
                        // loadUser(user)
                        store.createUser(user)
                        upDateUI.invoke(user)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    //todo error
                   // onUpdateUI.invoke(null)
                }

                // ...
            }
    }

}