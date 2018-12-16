package com.nunez.libellis.repository

import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nunez.libellis.TestActivity
import com.nunez.libellis.UserPrefsManager
import com.nunez.libellis.domain.entities.CurrentlyReadingBook
import com.vicpin.krealmextensions.queryAll
import io.realm.Realm
import io.realm.RealmConfiguration
import junit.framework.Assert.fail
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CurrentlyReadingBookRespositoryTest {

    @get:Rule
    val activityRule = ActivityTestRule(TestActivity::class.java)

    lateinit var realm: Realm
    lateinit var repository: CurrentlyReadingBookRespository
    lateinit var userPreference: UserPrefsManager

    @Before
    fun setup() {
        val testConfig = RealmConfiguration.Builder()
                .inMemory()
                .name("test-realm")
                .build()

        Realm.setDefaultConfiguration(testConfig)
        realm = Realm.getInstance(testConfig)
        userPreference = UserPrefsManager(InstrumentationRegistry.getContext())
        repository = CurrentlyReadingBookRespository(realm, userPreference)
    }

    @Test
    fun shouldRefreshAllObserverWhenStoringAllAndSetCurrentlyReadingIsCached() {
        // Given
        val list = listOf(CurrentlyReadingBook(), CurrentlyReadingBook(), CurrentlyReadingBook())
        var canAssert = false

        //when
        repository.all
                .subscribe({
                    if (canAssert) {
                        assertEquals("ListWith same length", list.size, it.size)
                    } else {
                    }
                }, {
                    fail("On error")
                })

        // Then
        repository.storeAll(list)
                .subscribe({
                    canAssert = true
                    Assert.assertEquals(userPreference.isCurrentlyReadingBooksSavedInCache(), true)
                }, {})
    }


    @Test
    fun shouldReplaceOldListWithNew() {
        // Given
        val list_1 = listOf(CurrentlyReadingBook(), CurrentlyReadingBook(), CurrentlyReadingBook())
        val list_2 = listOf(CurrentlyReadingBook(), CurrentlyReadingBook())

        repository.storeAll(list_1)
                .subscribe({}, {
                    fail("On error $it")
                })

        // When
        repository.replaceAll(list_2)
                .subscribe({
                    //Then
                    val retrievedBooks = CurrentlyReadingBook().queryAll()
                    assertEquals("List should be the same size", list_2.size, retrievedBooks.size)
                }, {
                    fail("On error $it")
                })
    }


    @Test
    fun shouldReturnDataWhenSubscribedInFirstTime() {
        // Given
        val list_1 = listOf(CurrentlyReadingBook(), CurrentlyReadingBook(), CurrentlyReadingBook())


        realm.beginTransaction()
        realm.delete(CurrentlyReadingBook::class.java)
        realm.commitTransaction()

        repository.storeAll(list_1)
                .subscribe({}, {
                    fail("On error $it")
                })

        repository.all.subscribe({ t: List<CurrentlyReadingBook>? ->
            assertEquals("List should be the same size", list_1.size, t?.size)
        }, {
            fail("On error $it")
        }, {
        })
    }
}