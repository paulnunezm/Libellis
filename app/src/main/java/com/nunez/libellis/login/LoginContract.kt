package com.nunez.libellis.login

/**
 * Created by paulnunez on 4/25/17.
 */
interface LoginContract {
    interface View{
        fun showAuthDialog(authorizationUrl: String?)
        fun showProgress()
        fun showError()
        fun goToUpdatesActivity()
    }

    interface Presenter{
        fun loginButtonClicked()
    }
}