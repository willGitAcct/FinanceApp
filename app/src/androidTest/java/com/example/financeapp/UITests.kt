package com.example.financeapp

import android.content.Context
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.financeapp.ui.SignUpScreen
import com.example.financeapp.ui.theme.SignInScreen
import com.example.financeapp.ui.theme.UsersApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
class TestNavHostController(context: Context) : NavHostController(context)
class UITests {
    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeTestRule = createComposeRule()


    //testing the main screen ui
    @Test
    fun TitleTest(){
        composeTestRule.setContent {
            UsersApplication()
        }

        composeTestRule.onNodeWithText("Welcome!").assertExists()
    }

    @Test
    fun TestRegisterBtn(){
        composeTestRule.setContent {
            UsersApplication()
        }

        composeTestRule.onNode(hasContentDescription("Register")).performClick()
    }

    @Before
    fun setUpNav(){
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }
    @Test
    fun TestSignUpBtn(){
        composeTestRule.setContent {
            SignUpScreen(navController = navController)
        }

        composeTestRule.onNode(hasContentDescription("stonk")).assertExists()
        composeTestRule.onNode(hasContentDescription("EmailInput")).assertExists()

    }

}