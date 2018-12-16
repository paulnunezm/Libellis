package com.nunez.libellis.domain.userId

import io.reactivex.Single

interface UserIdRepository {
    fun getUserId(): Single<String>
}