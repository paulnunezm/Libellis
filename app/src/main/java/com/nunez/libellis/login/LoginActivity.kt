package com.nunez.libellis.login

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.ConnectivityCheckerImpl
import com.nunez.libellis.R
import com.nunez.libellis.main.MainActivity
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.showSnackbar
import com.nunez.oauthathenticator.AuthDialog
import com.nunez.oauthathenticator.Authenticator
import kotlinx.android.synthetic.main.login_activity.*
import java.util.*
import kotlin.concurrent.schedule


class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginContract.Presenter
    lateinit var interactor: LoginContract.Interactor
    val loginButton by lazy { findViewById<Button>(R.id.loginButton) }
    val progress by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        presenter = LoginPresenter(this)
        interactor = LoginInteractor(this, ConnectivityCheckerImpl(this))

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

        Timer("ShowLoginButton").schedule(2000) {
            runOnUiThread {
                loginButton.visibility = VISIBLE
            }
        }

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

    override fun showLoginButton() {
        loginButton.visibility = VISIBLE
        progress.visibility = GONE
    }

    override fun goToUpdatesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onAuthDialogClose() {
        loginButton.visibility = VISIBLE
        progress.visibility = GONE
    }

    override fun showUnexpectedErrorMessage() {
        loginContainer.showSnackbar(getString(R.string.msg_error_implicit_error), Snackbar.LENGTH_LONG)
    }

    override fun showConnectivityErrorMessage() {
        loginContainer.showSnackbar(getString(R.string.msg_error_no_internet), Snackbar.LENGTH_LONG)
    }
}
