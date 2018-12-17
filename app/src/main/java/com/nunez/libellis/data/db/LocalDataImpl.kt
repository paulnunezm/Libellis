package com.nunez.libellis.data.db

import com.nunez.libellis.data.repository.LocalData

class LocalDataImpl(
        private val preferencesManager: PreferencesManager
) : LocalData {
    override fun getUserId(): String {
        return preferencesManager.userId
    }
}