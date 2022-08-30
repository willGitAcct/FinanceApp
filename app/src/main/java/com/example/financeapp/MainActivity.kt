package com.example.financeapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.ui.BottomNavHost
import com.example.financeapp.ui.BottomNavScreen
import com.example.financeapp.ui.Screen
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.example.financeapp.ui.theme.SignInScreen
import com.example.financeapp.ui.theme.UsersApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

        setContent {

            FinanceAppTheme {
              //SignInScreen()
                UsersApplication()
//                Scaffold(bottomBar = {BottomNavScreen(navController = navController, item = listItems)
//                }) {
//                    BottomNavHost(navHostController = navController)
//
//                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FinanceAppTheme {
        //SignInScreen()
        UsersApplication()
    }
}

// TODO: - attach api!! find way to update user stock list.
//  improve UI. Add a bank balance, and second table
//  Fix fields (password, crash when empty, etc)
//  maybe instead of news, add a 'learning' page(s) - definitions
//
//  also, user stories, and all the other stuff.