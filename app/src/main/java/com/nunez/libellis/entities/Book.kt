package com.nunez.libellis.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Required

data class Book(
        @Required val id: String = "",
        @Required val title: String = "",
        @Required val author: RealmList<Author> = RealmList()
): RealmObject()

data class Author(
        @Required val id: String = "",
        @Required val name: String = ""
): RealmObject()