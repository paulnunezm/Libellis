package com.nunez.libellis.ui.screens.login

import android.net.Uri

sealed class LoginState {
    object Success : LoginState()
    object Error : LoginState()
    object GetRequestToken : LoginState()
    class GetUserSecretKeys(val authToken:Uri) : LoginState()
    class ShowAuthenticationDialog(val authorizationUrl: String):LoginState()
}