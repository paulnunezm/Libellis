package com.nunez.libellis.ui.screens.login

import android.content.Context
import android.content.SharedPreferences
import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.NoConnectivityException
import com.nunez.libellis.R
import com.nunez.libellis.data.network.GoodreadsService
import com.nunez.libellis.data.network.SignedHttpClient
import com.nunez.libellis.data.network.SignedRetrofit
import com.nunez.libellis.data.network.parsers.UserIdParser
import com.nunez.libellis.data.repository.UserIdRepositoryImpl
import com.nunez.libellis.domain.userId.UserIdUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.Request

class LoginInteractor(
        private val context: Context,
        private val connectivityChecker: ConnectivityChecker
) : LoginContract.Interactor {

    var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)

    override fun hasConnection(): Boolean =
            connectivityChecker.isConected()

    override fun saveUserKeys(userKey: String?, userSecret: String?) {

        val editor = prefs.edit()

        editor.apply {
            putString(context.getString(R.string.user_prefs_user_key), userKey)
            putString(context.getString(R.string.user_prefs_user_secret), userSecret)
        }
        editor.apply()
    }

    override fun requestUserId(): Completable {
        return Completable.fromAction {
            val repository = UserIdRepositoryImpl(context)
            UserIdUseCase(repository)
                    .getUserId().subscribe({
                        saveUserId(it, {})
                    },{
                    })
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveUserId(userId: String, completed: () -> Unit) {
        val editor = prefs.edit()

        editor.apply {
            putString(context.getString(R.string.user_prefs_user_id), userId)
            putBoolean(context.getString(R.string.user_prefs_logged_in), true)
            apply()
        }
        completed.invoke()
    }

}