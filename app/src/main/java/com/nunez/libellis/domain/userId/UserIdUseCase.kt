package com.nunez.libellis.domain.userId

import io.reactivex.Single

class UserIdUseCase(
        private val repository: UserIdRepository) {

    fun getUserId(): Single<String> {
        return repository.getUserId()
    }
}