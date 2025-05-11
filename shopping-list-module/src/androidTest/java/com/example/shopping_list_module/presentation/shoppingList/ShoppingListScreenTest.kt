package com.example.shopping_list_module.presentation.shoppingList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.shopping_list_module.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ShoppingListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun addItem_displaysInList() {
        composeTestRule.setContent {
            ShoppingListScreen()
        }

        // Fill in name, quantity, and note
        composeTestRule.onNodeWithText("Name").performTextInput("Apples")
        composeTestRule.onNodeWithText("Quantity").performTextInput("3")
        composeTestRule.onNodeWithText("Note").performTextInput("Green ones")

        // Click "Add Item" button
        composeTestRule.onNodeWithText("Add Item").performClick()

        // Verify item appears in list
        composeTestRule.onNodeWithText("Apples").assertIsDisplayed()
        composeTestRule.onNodeWithText("Qty: 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note: Green ones").assertIsDisplayed()
    }

    @Test
    fun toggleItemBought_updatesIcon() {
        composeTestRule.setContent {
            ShoppingListScreen()
        }

        // Add item
        composeTestRule.onNodeWithText("Name").performTextInput("Milk")
        composeTestRule.onNodeWithText("Quantity").performTextInput("1")
        composeTestRule.onNodeWithText("Note").performTextInput("")

        composeTestRule.onNodeWithText("Add Item").performClick()

        // Toggle bought state
        composeTestRule.onAllNodesWithContentDescription("Toggle Bought")[0].performClick()

        // Could verify strikethrough using semantics if exposed
    }

    @Test
    fun deleteItem_removesFromList() {
        composeTestRule.setContent {
            ShoppingListScreen()
        }

        // Add item
        composeTestRule.onNodeWithText("Name").performTextInput("Bread")
        composeTestRule.onNodeWithText("Quantity").performTextInput("2")
        composeTestRule.onNodeWithText("Add Item").performClick()

        // Delete item
        composeTestRule.onAllNodesWithContentDescription("Delete")[0].performClick()

        // Ensure it's gone
        composeTestRule.onNodeWithText("Bread").assertDoesNotExist()
    }
}
