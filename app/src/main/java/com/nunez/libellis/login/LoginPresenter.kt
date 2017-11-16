package com.nunez.libellis.login

import android.net.Uri
import com.nunez.libellis.NoConnectivityException
import com.nunez.oauthathenticator.AuthDialog
import com.nunez.oauthathenticator.Authenticator
import java.util.concurrent.ExecutionException

/**
 * Created by paulnunez on 4/25/17
 */
class LoginPresenter(
        val view: LoginContract.View)
    : LoginContract.Presenter, Authenticator.AuthenticatorListener, AuthDialog.RequestListener {

    lateinit var authenticator: Authenticator
    lateinit var interactor: LoginContract.Interactor

    init {
        view.showLoginButton()
    }

    override fun loginButtonClicked() {
        if (interactor.hasConnection()) {
            view.showProgress()
            try {
                authenticator.getRequestToken()
            } catch (e: Exception) {
                view.showUnexpectedErrorMessage()
            }
        } else {
            view.showConnectivityErrorMessage()
        }
    }

    override fun onRequestTokenReceived(authorizationUrl: String?, requestToken: String?, requestTokenSecret: String?) {
        view.showAuthDialog(authorizationUrl)
    }

    override fun onAuthorizationTokenReceived(authToken: Uri?) {
        try {
            authenticator.getUserSecretKeys(authToken)
        } catch (e: ExecutionException) {
            view.showUnexpectedErrorMessage()
        }
    }

    override fun onUserSecretRecieved(userKey: String?, userSecret: String?) {
        interactor.saveUserKeys(userKey, userSecret)
        interactor.requestUserId()
                .subscribe(
                        this::onLoginSuccess,
                        { onLoginError(it) })
    }

    override fun setLoginInteractor(interactor: LoginContract.Interactor) {
        this.interactor = interactor
    }

    override fun onDialogCloseByUser() {
        view.onAuthDialogClose()
    }

    private fun onLoginSuccess() {
        view.showProgress()
        view.goToUpdatesActivity()
    }

    private fun onLoginError(e: Throwable) {
        if (e is NoConnectivityException) {
            view.showConnectivityErrorMessage()
        } else {
            view.showUnexpectedErrorMessage()
        }
    }
}