package com.nunez.libellis.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import com.nunez.libellis.main.MainActivity
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.showSnackbar
import com.nunez.oauthathenticator.AuthDialog
import com.nunez.oauthathenticator.Authenticator
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginContract.Presenter
    lateinit var interactor: LoginContract.Interactor
    val loginButton by lazy { findViewById<Button>(R.id.loginButton)  }
    val progress by lazy { findViewById<ProgressBar>(R.id.progressBar)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        presenter = LoginPresenter(this)
        interactor = LoginInteractor(this)

        val authenticator = Authenticator.Builder()
                .consumerKey(BuildConfig.GOODREADS_API_KEY)
                .consumerSecret(BuildConfig.GOODREADS_API_SECRET)
                .requestTokenUrl(GoodreadsService.REQUEST_TOKEN_URL)
                .accessTokenUrl(GoodreadsService.ACCESS_TOKEN_URL)
                .authorizeUrl(GoodreadsService.AUTHORIZE_URL)
                .callbackUrl(BuildConfig.CALLBACK_URL)
                .listener(presenter as LoginPresenter)
                .build()

        (presenter as LoginPresenter).authenticator = authenticator
        presenter.setLoginInteractor(interactor)

        loginButton.setOnClickListener { presenter.loginButtonClicked() }
        progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)

    }

    override fun showAuthDialog(authorizationUrl: String?) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")

        if (prev != null) ft.remove(prev)

        ft.addToBackStack(null)

        val authDialog = AuthDialog.newInstance(authorizationUrl, BuildConfig.CALLBACK_URL,
                presenter as LoginPresenter)
        authDialog.show(ft, "dialog")
    }

    override fun showProgress() {
        progress.visibility = VISIBLE
        loginButton.visibility = GONE
    }

    override fun showError() {
        loginButton.visibility = VISIBLE
        progress.visibility = GONE

        loginContainer.showSnackbar("Snap! something was wrong...")
    }

    override fun goToUpdatesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun showLoginButton(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slide: android.transition.Transition = Slide(Gravity.BOTTOM)
            slide.duration = 2000
            slide.startDelay = 500
            slide.interpolator = DecelerateInterpolator(2f)
            android.transition.TransitionManager.beginDelayedTransition(loginContainer, slide)
        }else{
            val fade = Fade()
            fade.duration = 500
            fade.startDelay = 500
            TransitionManager.beginDelayedTransition(loginContainer, fade)
        }

        loginCard.visibility = View.VISIBLE

        val animator = ObjectAnimator.ofFloat(loginButton,"alpha", 1f)
        animator.duration = 500
        animator.startDelay = 1000
        animator.start()
    }

    override fun onAuthDialogClose() {
        loginButton.visibility = VISIBLE
        progress.visibility = GONE
    }
}
