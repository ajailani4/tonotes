package com.tonotes.core.util

class Constants {
    object Network {
        const val BASE_URL = "https://tonotes-backend.herokuapp.com/api/v1/"
    }

    object DataStore {
        const val PREFERENCES_NAME = "appPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }

    object NavArgument {
        const val NOTE_ID = "noteId"
    }

    object TestTag {
        const val TITLE_TEXT_FIELD = "title-textfield"
        const val DESCRIPTION_TEXT_FIELD = "description-textfield"
        const val TITLE_TEXT = "title-text"
        const val DESCRIPTION_TEXT = "description-text"
    }
}