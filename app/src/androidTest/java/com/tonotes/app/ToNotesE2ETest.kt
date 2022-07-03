package com.tonotes.app

import androidx.compose.ui.test.*
import com.tonotes.core.R
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import com.tonotes.app.repository.NoteRepositoryFake
import com.tonotes.core.Constants.TestTag
import com.tonotes.core.Constants.NavArgument
import com.tonotes.note_domain.use_case.*
import com.tonotes.note_ui.add_edit_note.AddEditNoteViewModel
import com.tonotes.note_ui.home.HomeViewModel
import com.tonotes.note_ui.note_detail.NoteDetailViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ToNotesE2ETest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            App {
                Content()
            }
        }
    }

    @Test
    fun addNewNote() {
        val activity = composeTestRule.activity

        composeTestRule
            .onNodeWithContentDescription("Add icon")
            .performClick()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.add_note))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("Note title")

        composeTestRule
            .onNodeWithTag(TestTag.DESCRIPTION_TEXT_FIELD)
            .performTextInput("Note description")

        composeTestRule
            .onNodeWithText(activity.getString(R.string.save))
            .performClick()

        composeTestRule
            .onNodeWithText("Note title", useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Note description", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}