package ru.thstdio.study.ourplans.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import androidx.appcompat.app.AppCompatActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login_motion_layout.*
import kotlinx.android.synthetic.main.main_no_login_user.*
import org.koin.android.ext.android.inject
import ru.thstdio.study.ourplans.R
import ru.thstdio.study.ourplans.db.firebase.FireBaseAuth
import ru.thstdio.study.ourplans.db.repo.Repo.Companion.USER_STATUS_NOT_AUTH
import ru.thstdio.study.ourplans.ui.UserActivity


class LoginActivity : AppCompatActivity() {
    //Google Login Request Code
    private val RC_SIGN_IN = 7

    private val firebaseAuth: FireBaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_motion_layout)
        window.decorView.systemUiVisibility =
            SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        listenerFBUser()
        firebaseAuth.checkIsUserLogin()
        button_sing.setOnClickListener { signIn() }
      }

    @SuppressLint("CheckResult")
    fun listenerFBUser() {

        firebaseAuth.userAuthStatus
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ status ->
                when (status) {
                    USER_STATUS_NOT_AUTH -> showAuthView()
                    else->startUserActivity()
                }
            }
                , { error -> {} })
    }

    private fun signIn() {
        val signInIntent = firebaseAuth.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            data?.let { firebaseAuth.onActivityResult(data)}
        }
    }

    private fun startUserActivity() {
        firebaseAuth.userAuthStatus.onComplete()
        startActivity(
            Intent(
                baseContext,
                UserActivity::class.java
            )
        )
        finish()
    }

    private fun showAuthView() {
        motionLayout.transitionToEnd()
    }
}
