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
import com.example.financeapp.ui.StockAppLaunch
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.example.financeapp.ui.theme.SignInScreen
import com.example.financeapp.ui.theme.UsersApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

private lateinit var auth: FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

        setContent {

            FinanceAppTheme {
              //SignInScreen()
                UsersApplication()
                //StockAppLaunch()
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
