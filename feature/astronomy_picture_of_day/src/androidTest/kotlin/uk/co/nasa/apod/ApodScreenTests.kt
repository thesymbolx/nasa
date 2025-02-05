package uk.co.nasa.apod

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import uk.co.nasa.network.responseModel.MediaType

class ApodScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun todayApodIsDisplaying() {
        val title = "Lorem ipsum dolor sit amet, consectetur."
        val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque id libero consectetur, rhoncus velit sed, commodo augue. Mauris quam mi."

        composeTestRule.setContent {
            val uiState = ApodUiState(
                isLoading = false,
                isError = false,
                todayApod = ApodStateItem(
                    apodUrl = "www.example.com",
                    title = title,
                    description = description,
                    favorite = true,
                    mediaType = MediaType.IMAGE
                )
            )

            ApodScreen(
                todayApod = uiState.todayApod!!,
                historicApod = persistentListOf(),
                imageLoaded = {},
                imageSelected = {},
                onFavoriteClick = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
    }

    @Test
    fun checkHistoricalApod() {
        val historicApodTitle1 = "hs 1 title"
        val historicApodDescription1 = "hs 1 description"

        val historicApodTitle2 = "hs 2 title"
        val historicApodDescription2 = "hs 2 description"

        composeTestRule.setContent {
            val uiState = ApodUiState(
                isLoading = false,
                isError = false,
                todayApod = ApodStateItem(
                    apodUrl = "www.example.com",
                    title = "Lorem ipsum dolor sit amet, consectetur.",
                    description = "Lorem ipsum dolor sit amet.",
                    favorite = true,
                    mediaType = MediaType.IMAGE
                ),
                historicApod = persistentListOf(
                    ApodStateItem(
                        apodUrl = "www.example.com",
                        title = historicApodTitle1,
                        description = historicApodDescription1,
                        favorite = true,
                        mediaType = MediaType.IMAGE
                    ),
                    ApodStateItem(
                        apodUrl = "www.example.com",
                        title = historicApodTitle2,
                        description = historicApodDescription2,
                        favorite = true,
                        mediaType = MediaType.IMAGE
                    )
                )
            )

            ApodScreen(
                todayApod = uiState.todayApod!!,
                historicApod = uiState.historicApod,
                imageLoaded = {},
                imageSelected = {},
                onFavoriteClick = { _, _ -> }
            )
        }

        composeTestRule.onRoot().performTouchInput {
            swipeUp(startY = centerY)
        }

        composeTestRule.onNodeWithText(historicApodTitle1).assertIsDisplayed()
        composeTestRule.onNodeWithText(historicApodDescription1).assertIsDisplayed()
        composeTestRule.onNodeWithText(historicApodTitle2).assertIsDisplayed()
        composeTestRule.onNodeWithText(historicApodDescription2).assertIsDisplayed()
    }
}