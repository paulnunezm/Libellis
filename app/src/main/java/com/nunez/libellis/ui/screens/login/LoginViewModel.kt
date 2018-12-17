package com.nunez.libellis.ui.screens.login

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.data.db.PreferencesManager
import com.nunez.libellis.domain.userId.UserIdRepository
import com.nunez.libellis.ui.screens.ScreenState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
        private val repository: UserIdRepository,
        private val preferences: PreferencesManager,
        private val connectivityChecker: ConnectivityChecker
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _loginState: MutableLiveData<ScreenState<LoginState>> = MutableLiveData()

    val loginState: LiveData<ScreenState<LoginState>>
        get() = _loginState

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onLoginClicked() {
        if (connectivityChecker.isConected()) {
            _loginState.value = ScreenState.Loading
            _loginState.value = ScreenState.Render(LoginState.GetRequestToken)
        } else {
            _loginState.value = ScreenState.Render(LoginState.ConnectionError)
        }
    }

    fun onRequestTokenReceived(authorizationUrl: String) {
        _loginState.value = ScreenState.Render(LoginState.ShowAuthenticationDialog(authorizationUrl))
    }

    fun onAuthorizationTokenReceived(authToken: Uri) {
        _loginState.value = ScreenState.Render(LoginState.GetUserSecretKeys(authToken))
    }

    fun onUserScretRecieved(userKey: String, userSecret: String) {
        preferences.saveUserKeys(userKey, userSecret)

        val disposable = repository.getUserId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferences.userId = it
                    _loginState.value = ScreenState.Render(LoginState.Success)
                }, {
                    _loginState.value = ScreenState.Render(LoginState.Error)
                })
        compositeDisposable.add(disposable)
    }

    fun onAuthorizationError() {
        _loginState.value = ScreenState.Render(LoginState.Error)
    }

    fun onAuthDialogClosed() {
        _loginState.value = ScreenState.Render(LoginState.ShowLoginButton)
    }

}

class LoginViewModelFactory(
        val repository: UserIdRepository,
        val preferencesManager: PreferencesManager,
        val connectivityChecker: ConnectivityChecker
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, preferencesManager, connectivityChecker) as T
    }
}