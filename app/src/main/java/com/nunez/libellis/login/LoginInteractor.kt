package com.nunez.libellis.login

import android.content.Context
import android.content.SharedPreferences
import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.NoConnectivityException
import com.nunez.libellis.R
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.SignedRetrofit
import com.nunez.libellis.repository.parsers.UserIdParser
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
        val goodreadsService = SignedRetrofit(context).instance
                .create(GoodreadsService::class.java)

        val client = SignedHttpClient(context).instance
        val request = Request.Builder()
                .url(HttpUrl.parse(GoodreadsService.BASE_URL + GoodreadsService.USER_ID_ENDPOINT))
                .build()

        return Completable.create { emitter ->
            if (connectivityChecker.isConected()) {
                val response = client.newCall(request).execute()
                val userId = UserIdParser(response.body()?.string() as String).parse()

                saveUserId(userId, { emitter.onComplete() })
            } else {
                emitter.onError(NoConnectivityException())
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveUserId(userId: String, completed: () -> Unit) {
        val editor = prefs.edit()

        editor.apply {
            putString(context.getString(R.string.user_prefs_user_id), userId)
            putBoolean(context.getString(R.string.user_prefs_logged_in), true)
        }
        editor.apply()
        completed.invoke()
    }

}