package com.nunez.libellis.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.nunez.libellis.R
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.SignedRetrofit
import com.nunez.libellis.repository.parsers.UserIdParser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.Request

class LoginInteractor(val context: Context) : LoginContract.Interactor {

    var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)

    override fun saveUserKeys(userKey: String?, userSecret: String?) {

        val editor = prefs.edit()

        editor.apply {
            putString(context.getString(R.string.user_prefs_user_key), userKey)
            putString(context.getString(R.string.user_prefs_user_secret), userSecret)
        }
        editor.apply()
    }

    override fun requestUserId(): Observable<Unit> {
        Log.d("LoginInteractor", "requestingUserID")
        val goodreadsService = SignedRetrofit(context).instance
                .create(GoodreadsService::class.java)

        val client = SignedHttpClient(context).instance
        val request = Request.Builder()
                .url(HttpUrl.parse(GoodreadsService.BASE_URL + GoodreadsService.USER_ID_ENDPOINT))
                .build()

        return Observable.create<Unit>({
            subscriber ->

            val response = client.newCall(request).execute()
            val userId = UserIdParser(response.body()?.string() as String).parse()

            saveUserId(userId, {
                subscriber.onComplete()
            })

        }).subscribeOn(Schedulers.io())
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