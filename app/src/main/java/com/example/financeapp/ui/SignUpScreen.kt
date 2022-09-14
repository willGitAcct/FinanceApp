package com.example.financeapp.ui

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.financeapp.R
import com.example.financeapp.User
import com.example.financeapp.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.time.Duration

//for firebase
private lateinit var auth: FirebaseAuth

//@Preview
@Composable
fun SignUpScreen(navController: NavController) {

    val context = LocalContext.current//for toast
    auth = FirebaseAuth.getInstance()
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painterResource(id = R.drawable.stonks),
            contentDescription = "",
            contentScale = ContentScale.FillHeight, // or some other scale
            modifier = Modifier.fillMaxSize().semantics { contentDescription = "stonk" }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.padding(50.dp))
            Title("Registration")
            val formState = remember { mutableStateOf(TextFieldValue()) }
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val pwState = remember { mutableStateOf(TextFieldValue()) }

            TextField(
                modifier = Modifier.fillMaxWidth().semantics { contentDescription="EmailInput" },
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text(text = "Email") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f)

                ),
                shape = RoundedCornerShape(8.dp),
                //if needed
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = formState.value,
                onValueChange = { formState.value = it },
                label = { Text(text = "Username") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(8.dp),
                //if needed
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )
            val showPwState = remember { mutableStateOf(false) }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = pwState.value,
                onValueChange = { pwState.value = it },
                label = { Text(text = "Password") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (showPwState.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPwState.value) {
                        IconButton(onClick = { showPwState.value = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide pw"
                            )
                        }
                    } else {
                        IconButton(onClick = { showPwState.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "show pw"
                            )
                        }

                    }
                }
                //if needed
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    try {
                        auth.createUserWithEmailAndPassword(
                            emailState.value.text,
                            pwState.value.text
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //navController.navigate("home_page")
                                    Log.d(TAG, "success! check console")
                                    val user = User(emailState.value.text, formState.value.text, "")
                                    FirebaseAuth.getInstance().currentUser?.let { it1 ->
                                        FirebaseDatabase.getInstance().getReference("User")
                                            .child(it1.uid).setValue(user)
                                    }
                                    Toast.makeText(
                                        context,
                                        "Registered Successfully; logging in..",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("home_page")

                                } else {
                                    Log.d(TAG, "Failure.....", it.exception)

                                }
                            }
                    } catch (e:Exception){
                        Toast.makeText(
                            context,
                            "Error; make sure fields are entered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription="SignUp" },
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4974a5),
                    contentColor = Color.White
                )
            ) {
                Text("Sign Up")

            }
            //
        }
    }
}


