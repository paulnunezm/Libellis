package com.nunez.libellis.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ReactiveStore<Key, Model> {

    val all: Flowable<List<Model>>

    fun replaceAll(modelList: List<Model>): Completable

    fun storeAll(modelList: List<Model>): Completable

    fun storeSingular(model: Model): Completable

    fun getSingular(modelKey:Key): Single<Model>


}

