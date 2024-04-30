package com.example.marvel.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.marvel.presentation.theme.MarvelTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCircularProgressIndicatorShown_whenStateLoading() {
        composeTestRule.setContent {
            MarvelTheme {
                HomeScreen(viewModel = TestHomeViewModel(HomeUiState.Loading))
            }
        }

        composeTestRule
            .onNode(hasTestTag("progressIndicator"), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun testCircularProgressIndicatorGone_whenStateError() {
        composeTestRule.setContent {
            MarvelTheme {
                HomeScreen(viewModel = TestHomeViewModel(HomeUiState.Error(message = "Some error")))
            }
        }

        composeTestRule
            .onNode(hasTestTag("progressIndicator"), useUnmergedTree = true)
            .assertIsNotDisplayed()
    }

    @Test
    fun testErrorMessageDisplayed_whenStateError() {
        composeTestRule.setContent {
            MarvelTheme {
                HomeScreen(viewModel = TestHomeViewModel(HomeUiState.Error(message = "Some error")))
            }
        }

        composeTestRule
            .onNodeWithText("Some error")
            .assertIsDisplayed()
    }
}

class TestHomeViewModel(testUiState: HomeUiState) : HomeViewModel {
    override val uiState = MutableStateFlow(testUiState)
}
