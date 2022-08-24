package com.example.financeapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financeapp.ui.theme.Title
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//val user = Firebase.auth.currentUser
@Preview(showSystemUi = true)
@Composable
fun HomeScreen(){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Title("Home Screen")
        SearchField()
    }


}

@Composable
fun SearchField() {
    val searchState = remember{ mutableStateOf(TextFieldValue()) }
    val dbRef = FirebaseDatabase.getInstance().getReference("User")
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user!!.uid
    var stock: String = ""

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchState.value,
        onValueChange = {searchState.value = it},
        label = {Text(text= "Enter Stock ticker or symbol")},
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        shape = RoundedCornerShape(8.dp),
    )

    Text("Results: "+ searchState.value.text)

    Button(onClick = {
        //println(dbRef.child(uid).child("favourites").get().toString())
        //"com.google.android.gms.tasks.zzw@9a01f19"
       // stock.add()

        //this gets the value of the user's favourites list, before adding to it later when the button is clicked
//        dbRef.child(uid).child("favourites").addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val stocks: String? = snapshot.getValue(String::class.java)
//                println(stocks + "1")
//                if (stocks != null) {
//                    stock = stocks
//                    println(stock+"2")
//                }
//                stock+= searchState.value.text+","
//                //stock.add(searchState.value.text)
//                dbRef.child(uid).child("favourites").setValue(stock)
//                println(stock + "3")
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
        dbRef.child(uid).child("favourites").get().addOnSuccessListener {
            val stocks: String = it.getValue(String::class.java).toString()
                println(stocks + "1")
                    //val user: User = User()
                    stock = stocks
                    println(stock+"2")
                    stock+= searchState.value.text+","
                    //stock.add(searchState.value.text)
                    dbRef.child(uid).child("favourites").setValue(stock)


                println(stock + "3")
            Log.i("firebase", "Got value ${it.value}")

        }
        //println(stock)
        //stock = dbRef.child(uid).child("favourites").getValue


        //correctly finds the favourites category in the db
    },
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4974a5),
            contentColor = Color.White) )
        {
            Text("Set Stock as Favourite")
    }


}



