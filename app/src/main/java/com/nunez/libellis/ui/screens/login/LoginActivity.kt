package com.nunez.libellis.ui.screens.login

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import com.nunez.libellis.data.db.LocalDataImpl
import com.nunez.libellis.data.db.PreferencesManager
import com.nunez.libellis.data.network.GoodreadsService
import com.nunez.libellis.data.repository.UserIdRepositoryImpl
import com.nunez.libellis.showSnackbar
import com.nunez.libellis.ui.screens.ScreenState
import com.nunez.libellis.ui.screens.main.MainActivity
import com.nunez.oauthathenticator.AuthDialog
import com.nunez.oauthathenticator.Authenticator
import kotlinx.android.synthetic.main.login_activity.*
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity(),
        Authenticator.AuthenticatorListener,
        AuthDialog.RequestListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authenticator: Authenticator
    val loginButton by lazy { findViewById<Button>(R.id.loginButton) }
    val progress by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        authenticator = Authenticator.Builder()
                .consumerKey(BuildConfig.GOODREADS_API_KEY)
                .consumerSecret(BuildConfig.GOODREADS_API_SECRET)
                .requestTokenUrl(GoodreadsService.REQUEST_TOKEN_URL)
                .accessTokenUrl(GoodreadsService.ACCESS_TOKEN_URL)
                .authorizeUrl(GoodreadsService.AUTHORIZE_URL)
                .callbackUrl(BuildConfig.CALLBACK_URL)
                .listener(this)
                .build()

        val preferencesManager = PreferencesManager(this)
        val localData = LocalDataImpl(preferencesManager)
        val userIdRepo = UserIdRepositoryImpl(this, localData)

        viewModel = ViewModelProviders.of(
                this,
                LoginViewModelFactory(userIdRepo, preferencesManager)
        )[LoginViewModel::class.java]

        viewModel.loginState.observe(this, Observer<ScreenState<LoginState>> {
            updateUI(it)
        })

        loginButton.setOnClickListener {
            viewModel.onLoginClicked()
        }

        progressBar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)

        showLoginButton()
    }

    override fun onRequestTokenReceived(authorizationUrl: String?, requestToken: String?, requestTokenSecret: String?) {
        viewModel.onRequestTokenReceived(authorizationUrl as String)
    }

    override fun onAuthorizationTokenReceived(authToken: Uri?) {
        viewModel.onAuthorizationTokenReceived(authToken as Uri)
    }

    override fun onUserSecretRecieved(userKey: String?, userSecret: String?) {
        viewModel.onUserScretRecieved(userKey as String, userSecret as String)
    }

    override fun onDialogCloseByUser() {
    }

    private fun updateUI(screenState: ScreenState<LoginState>) {
        when (screenState) {
            ScreenState.Loading -> showProgress()
            is ScreenState.Render -> processLoginState(screenState.renderState)
        }
    }

    private fun processLoginState(renderState: LoginState) {
        when (renderState) {
            LoginState.GetRequestToken -> authenticator.getRequestToken()
            is LoginState.ShowAuthenticationDialog -> showAuthDialog(renderState.authorizationUrl)
            is LoginState.GetUserSecretKeys -> {
                try {
                    authenticator.getUserSecretKeys(renderState.authToken)
                } catch (e: ExecutionException) {
                    viewModel.onAuthorizationError()
                }
            }
            LoginState.Success -> goToUpdatesActivity()
            LoginState.Error -> showUnexpectedErrorMessage()
        }
    }

    private fun showAuthDialog(authorizationUrl: String?) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")

        if (prev != null) ft.remove(prev)

        ft.addToBackStack(null)

        val authDialog = AuthDialog.newInstance(authorizationUrl, BuildConfig.CALLBACK_URL,
                this)
        authDialog.show(ft, "dialog")
    }

    private fun showProgress() {
        progress.visibility = VISIBLE
        loginButton.visibility = GONE
    }

    fun showLoginButton() {
        progress.visibility = GONE
        Timer("ShowLoginButton").schedule(500) {
            runOnUiThread {
                loginButton.visibility = VISIBLE
            }
        }
    }

    private fun goToUpdatesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun onAuthDialogClose() {
        loginButton.visibility = VISIBLE
        progress.visibility = GONE
    }

    private fun showUnexpectedErrorMessage() {
        loginContainer.showSnackbar(getString(R.string.msg_error_implicit_error), Snackbar.LENGTH_LONG)
    }

    private fun showConnectivityErrorMessage() {
        loginContainer.showSnackbar(getString(R.string.msg_error_no_internet), Snackbar.LENGTH_LONG)
    }
}
