package com.tonotes.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.tonotes.core.util.Constants.TestTag
import com.tonotes.core.R
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

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = composeTestRule.activity

        composeTestRule.setContent {
            App {
                Content()
            }
        }
    }

    @Test
    fun addNewNote_getTheDetail_editIt_deleteIt() {
        // Add new note
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

        // Get the detail
        composeTestRule
            .onNodeWithText("Note title")
            .performClick()

        composeTestRule
            .onNodeWithTag(TestTag.TITLE_TEXT)
            .assertTextEquals("Note title")

        composeTestRule
            .onNodeWithTag(TestTag.DESCRIPTION_TEXT)
            .assertTextEquals("Note description")

        composeTestRule
            .onNodeWithContentDescription("Back icon")
            .performClick()

        // Edit the note
        composeTestRule
            .onNodeWithText("Note title")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("More vertical icon")
            .performClick()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.edit))
            .performClick()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.edit_note))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextReplacement("Edited note title")

        composeTestRule
            .onNodeWithTag(TestTag.DESCRIPTION_TEXT_FIELD)
            .performTextReplacement("Edited note description")

        composeTestRule
            .onNodeWithText(activity.getString(R.string.save))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Back icon")
            .performClick()

        composeTestRule
            .onNodeWithText("Edited note title", useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Edited note description", useUnmergedTree = true)
            .assertIsDisplayed()

        // Delete the note
        composeTestRule
            .onNodeWithText("Edited note title")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("More vertical icon")
            .performClick()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.delete))
            .performClick()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.yes))
            .performClick()

        composeTestRule
            .onNodeWithText("Edited note title")
            .assertDoesNotExist()
    }
}