package com.nunez.libellis.ui.screens.login

import android.net.Uri

sealed class LoginState {
    object Success : LoginState()
    object Error : LoginState()
    object ConnectionError : LoginState()
    object GetRequestToken : LoginState()
    object ShowLoginButton : LoginState()
    class GetUserSecretKeys(val authToken:Uri) : LoginState()
    class ShowAuthenticationDialog(val authorizationUrl: String):LoginState()
}