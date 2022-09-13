package com.example.financeapp

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.ui.BottomNavHost
import com.example.financeapp.ui.BottomNavScreen
import com.example.financeapp.ui.Screen
import com.example.financeapp.ui.theme.Title
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//val user = Firebase.auth.currentUser
//@Preview(showSystemUi = true)
@Composable
fun navBar() {
    val listItems = listOf(Screen.Search, Screen.Calculator, Screen.History,Screen.Settings )
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavScreen(navController = navController, item = listItems)
    }) {
        BottomNavHost(navHostController = navController)


    }
}




//    @Composable
//    fun HomeScreen() {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            Title("Welcome!")
//            SettingsPage()
//            //SearchField()
//        }
//
//
//    }
    @Preview
    @Composable
    fun SettingsPage(){
    val mContext = LocalContext.current

    val auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painterResource(id = R.drawable.stonks),
            contentDescription = "",
            contentScale = ContentScale.FillHeight, // or some other scale
            modifier = Modifier.fillMaxSize(),
            alpha = 0.6f
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            
            Spacer(modifier = Modifier.padding(100.dp))
            Button(
                onClick = {
                    auth.sendPasswordResetEmail(user.email.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(mContext, "Password Reset Link Sent to Email", Toast.LENGTH_LONG).show()                        }
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
                Text(
                    text = "Reset Password"
                )

            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4974a5),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Feedback"
                )

            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4974a5),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "About"
                )

            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4974a5),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Logout"
                )

            }


        }
    }
    }
    @Composable
    fun SearchField() {
        val searchState = remember { mutableStateOf(TextFieldValue()) }
        val dbRef = FirebaseDatabase.getInstance().getReference("User")
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid
        var stock: String = ""

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchState.value,
            onValueChange = { searchState.value = it },
            label = { Text(text = "Enter Stock ticker or symbol") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
        )

        //Text("Results: " + searchState.value.text)

        Button(
            onClick = {

                dbRef.child(uid).child("favourites").get().addOnSuccessListener {
                    val stocks: String = it.getValue(String::class.java).toString()
                    println(stocks + "1")
                    //val user: User = User()
                    stock = stocks
                    println(stock + "2")
                    stock += searchState.value.text + ","
                    //stock.add(searchState.value.text)
                    dbRef.child(uid).child("favourites").setValue(stock)


                    println(stock + "3")
                    Log.i("firebase", "Got value ${it.value}")

                }
                //println(stock)
                //stock = dbRef.child(uid).child("favourites").getValue


            },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF4974a5),
                contentColor = Color.White
            )
        )
        {
            Text("Set Stock as Favourite")
        }


    }




