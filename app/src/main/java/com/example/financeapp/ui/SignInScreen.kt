package com.example.financeapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.HomeScreen
import com.example.financeapp.ui.SignUpScreen


//@PreviewParameter()
@Composable
fun SignInScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Title("Welcome!")
        Email()
        Password()
        SignInBtn(navHome = {navController.navigate("home_page")})
        TextButton(onClick = { navController.navigate("sign_up")}) {
            Text(text = "Click here to register",
                style = TextStyle(textDecoration = TextDecoration.Underline))
        }

    }
}

//for navigating to the other page----------------------------------------------------------------------
@Preview
@Composable
fun UsersApplication(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "sign_in") {
        composable("sign_in"){
            SignInScreen(navController)
        }
        composable("home_page"){
            HomeScreen()
        }
        composable(route = "sign_up"){
            SignUpScreen()
        }
    }
}
//---------------------------------------------------------------------------------------------------
//
//@Composable
//no need for this, can just add to the one navigation fn
//fun navSignUp(){
//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = "sign_in") {
//            composable("sign_in"){
//                SignInScreen(navController)
//            }
//            composable("sign_up"){
//                SignUpScreen()
//            }
//        }
//}

@Composable
fun SignInBtn(navHome: () -> Unit) {
  Button(
      onClick = navHome,
      modifier = Modifier
          .fillMaxWidth(),
      contentPadding = PaddingValues(16.dp),
      colors = ButtonDefaults.buttonColors(
          backgroundColor = Color( 0xFF4974a5),
          contentColor = Color.White
      ) ) {
      Text(
          text = "Sign In"
      )

  }
}

@Composable
fun Email() {
    val emailState = remember{ mutableStateOf(TextFieldValue()) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = emailState.value,
        onValueChange = {emailState.value = it},
        label = {Text(text= "Email")},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


//template for a field composable with the same presets as the others
//
//@Composable

@Composable
fun FormField(label: String) {
    val formState = remember{ mutableStateOf(TextFieldValue()) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = formState.value,
        onValueChange = {formState.value = it},
        label = {Text(text= label)},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        //if needed
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun Password() {
    val pwState = remember{ mutableStateOf(TextFieldValue()) }
    val showPwState = remember { mutableStateOf(false)}
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = pwState.value,
        onValueChange = {pwState.value = it},
        label = {Text(text= "Password")},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (showPwState.value) {
            VisualTransformation.None
        } else {PasswordVisualTransformation()},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if(showPwState.value){
                IconButton(onClick = { showPwState.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide pw")
                }
            } else {
                IconButton(onClick = { showPwState.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "show pw")
                }

            }
        }
        )}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3
    )}
