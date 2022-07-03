package com.tonotes.app

import androidx.compose.ui.test.*
import com.tonotes.core.R
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.tonotes.core.Constants.TestTag
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
    fun addNewNote() {
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