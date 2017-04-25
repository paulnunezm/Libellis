package com.nunez.libellis.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.showSnackbar
import com.nunez.libellis.updates.UpdatesActivity
import com.nunez.oauthathenticator.AuthDialog
import com.nunez.oauthathenticator.Authenticator
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginContract.Presenter
    val loginButton by lazy { findViewById(R.id.loginButton) as Button }
    val progress by lazy { findViewById(R.id.progressBar) as ProgressBar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        presenter = LoginPresenter(this)

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

        loginButton.setOnClickListener { presenter.loginButtonClicked() }

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
        val intent = Intent(this, UpdatesActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
