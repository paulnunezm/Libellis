package com.nunez.libellis.login

import android.net.Uri
import android.util.Log
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
        view.showProgress()

        try {
            authenticator.getRequestToken()
        } catch (e: Exception) {
            view.showError()
        }
    }

    override fun onRequestTokenReceived(authorizationUrl: String?, requestToken: String?, requestTokenSecret: String?) {
        view.showAuthDialog(authorizationUrl)
    }

    override fun onAuthorizationTokenReceived(authToken: Uri?) {
        try {
            authenticator.getUserSecretKeys(authToken)
        } catch (e: ExecutionException) {
            view.showError()
        }
    }

    override fun onUserSecretRecieved(userKey: String?, userSecret: String?) {
        interactor.saveUserKeys(userKey, userSecret)
        interactor.requestUserId()
                .subscribe({},
                        { t ->
                            Log.e("loginPresenter", "onError: ${t.toString()}")
                            // TODO: HANDLE ERROR
                        },
                        {
                            view.goToUpdatesActivity()
                        })
    }

    override fun setLoginInteractor(interactor: LoginContract.Interactor) {
        this.interactor = interactor
    }

    override fun onDialogCloseByUser() {
        view.onAuthDialogClose()
    }
}