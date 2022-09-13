package com.example.financeapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
@Preview(showSystemUi = true)
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




    @Composable
    fun HomeScreen() {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Title("Welcome!")
            HomePlaceholder()
            //SearchField()
        }


    }
    @Composable
    fun HomePlaceholder(){
        Text(text = "Enjoy the app!\nNavigate using the bar on the bottom of the screen.\n" +
                "\nYou can browse or search for a specific stock using the search page.\n" +
                "\nYou may use the investment calculator to simulate investment performance.\n" +
                "\nFinally, check out the learning page to get educated about investment topics! "
        )
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




