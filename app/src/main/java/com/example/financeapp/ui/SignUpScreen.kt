package com.example.financeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financeapp.ui.theme.*


@Preview
@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Title("Sign Up Here!")

        FormField(label = "Username")
        FormField(label = "Email")
        FormField(label = "Password")
        FormField(label = "Re-Enter Password")
        //FormField(label = )

        SignUpBtn()
        //Email()
        //Password()
        //SignInBtn(navHome = {navController.navigate("home_page")})

    }
}

@Composable
fun SignUpBtn() {
    //TODO("Not yet implemented")
    Button(onClick = { },
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color( 0xFF4974a5),
            contentColor = Color.White
        )) {
        Text("Sign Up Button")

    }
}

//@Composable
//fun signUp() {
//
//
//}
