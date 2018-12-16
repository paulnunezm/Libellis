package com.nunez.libellis.data.repository

import android.content.Context
import com.nunez.libellis.data.network.GoodreadsService
import com.nunez.libellis.data.network.SignedHttpClient
import com.nunez.libellis.data.network.SignedRetrofit
import com.nunez.libellis.data.network.parsers.UserIdParser
import com.nunez.libellis.domain.userId.UserIdRepository
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.Request

class UserIdRepositoryImpl(val context: Context) : UserIdRepository {
    override fun getUserId(): Single<String> {

        return Single.create<String> {

            val client = SignedHttpClient(context).instance
            val requestURL = HttpUrl.parse(GoodreadsService.BASE_URL + GoodreadsService.USER_ID_ENDPOINT).toString()
            val request = Request.Builder()
                    .url(requestURL)
                    .build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val userId = UserIdParser(response.body()?.string() as String).parse()

                if(userId.isNotEmpty()){
                    it.onSuccess(userId)
                }else{
                    it.onError(Throwable("Error parsing user id"))
                }
            } else {
                it.onError(Throwable(response.message()))
            }
        }
    }
}