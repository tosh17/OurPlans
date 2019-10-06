package ru.thstdio.study.ourplans

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_no_login_user.*
import ru.thstdio.study.ourplans.db.entity.TestDoc
import ru.thstdio.study.ourplans.db.firebase.FireBaseAuth
import ru.thstdio.study.ourplans.ui.UserActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    //Google Login Request Code
    private val RC_SIGN_IN = 7
    @Inject
    lateinit var firebaseAuth: FireBaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
            SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        App.daggerComponent.inject(this)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.getCurrentUser()
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = firebaseAuth.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            data?.let { firebaseAuth.onActivityResult(data) { currentUser -> updateUI(currentUser) } }
        }
    }


    private fun loadUser(user: FirebaseUser?) {
        user?.let {
            val db = FirebaseFirestore.getInstance()
            var messageRef = db.collection("users").document(user.uid)
        }


    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            //Do your Stuff
            Toast.makeText(this, "Hello ${user.displayName}", Toast.LENGTH_LONG).show()
            Log.d(" user.photoUrl", user.photoUrl.toString())
            //     startActivity(Intent(baseContext, UserActivity::class.java))
        } else {
            cardView.visibility = View.VISIBLE
            viewStub_not_login?.let {
                it.inflate()
                sign_in_button.setOnClickListener { signIn() }
            }
        }
    }
}
