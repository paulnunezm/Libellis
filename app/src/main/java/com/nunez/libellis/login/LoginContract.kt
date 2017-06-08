package com.nunez.libellis.login

import io.reactivex.Observable

/**
 * Created by paulnunez on 4/25/17
 */
interface LoginContract {
    interface View {
        fun showAuthDialog(authorizationUrl: String?)
        fun showProgress()
        fun showError()
        fun goToUpdatesActivity()
        fun showLoginButton()
        fun onAuthDialogClose()
    }

    interface Presenter {
        fun loginButtonClicked()
        fun setLoginInteractor(interactor: LoginContract.Interactor)
    }

    interface Interactor {
        fun saveUserKeys(userKey: String?, userSecret: String?)
        fun requestUserId(): Observable<Unit>
        fun saveUserId(userId: String, completed: () -> Unit)
    }
}