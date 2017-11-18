package com.nunez.libellis.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Required

open class Book(
        @Required var id: String = "",
        @Required var title: String = "",
        @Required var author: RealmList<Author> = RealmList()
): RealmObject()

open class Author(
        @Required var id: String = "",
        @Required var name: String = ""
): RealmObject()


// Realm doesn't support polymorphism
open class CurrentlyReadingBook(
        @Required var id: String = "",
        @Required var title: String = "",
        @Required var author: RealmList<Author> = RealmList()
): RealmObject()