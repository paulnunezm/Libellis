package com.nunez.libellis.login

import io.reactivex.Completable

/**
 * Created by paulnunez on 4/25/17
 */
interface LoginContract {
    interface View {
        fun showAuthDialog(authorizationUrl: String?)
        fun showProgress()
        fun showUnexpectedErrorMessage()
        fun showConnectivityErrorMessage()
        fun goToUpdatesActivity()
        fun showLoginButton()
        fun onAuthDialogClose()
    }

    interface Presenter {
        fun loginButtonClicked()
        fun setLoginInteractor(interactor: LoginContract.Interactor)
    }

    interface Interactor {
        fun hasConnection(): Boolean
        fun saveUserKeys(userKey: String?, userSecret: String?)
        fun requestUserId(): Completable
        fun saveUserId(userId: String, completed: () -> Unit)
    }
}