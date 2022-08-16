package com.example.financeapp.ui

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financeapp.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.Duration

//for firebase
private lateinit var auth: FirebaseAuth

@Preview
@Composable
fun SignUpScreen() {
    auth = Firebase.auth
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){

        Title("Sign Up Here!")
    val formState = remember { mutableStateOf(TextFieldValue()) }
    val emailState = remember { mutableStateOf(TextFieldValue())}
    val pwState = remember { mutableStateOf(TextFieldValue())}

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text(text = "Email") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            //if needed
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = pwState.value,
        onValueChange = { pwState.value = it },
        label = { Text(text = "Password") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        //if needed
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

    )
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = formState.value,
        onValueChange = { formState.value = it },
        label = { Text(text = "Re-Enter Password") },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        //if needed
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
    Button(
        onClick = {
                  auth.createUserWithEmailAndPassword(emailState.value.text, pwState.value.text)
                      .addOnCompleteListener { if(it.isSuccessful){
                          Log.d(TAG,"success! check console")
                      } else {
                          Log.d(TAG,"Failure.....",it.exception)
                      }
                      }
                  },
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4974a5),
            contentColor = Color.White
        )
    ) {
        Text("Sign Up Button")

    }
//
    }
}

@Composable
fun formField() {

}
//}
//@Composable
//fun SignUpBtn() {
//    //TODO("Not yet implemented")
//    Button(onClick = {auth.signInWithEmailAndPassword(formState, password)
//        .addOnCompleteListener {
//            if (it.isSuccessful){
//                Log.d(TAG, "success")
//            }else {
//                Log.d(TAG, "failure")
//            }
//        }},
//        modifier = Modifier
//            .fillMaxWidth(),
//        contentPadding = PaddingValues(16.dp),
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = Color( 0xFF4974a5),
//            contentColor = Color.White
//        )) {
//        Text("Sign Up Button")
//
//    }
//}


