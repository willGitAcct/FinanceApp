package com.example.financeapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financeapp.ui.theme.SignInScreen
import com.example.financeapp.ui.theme.Title
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

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

        val stock: ArrayList<String> = ArrayList()
        stock.add(searchState.value.text)

        dbRef.child(uid).child("favourites").setValue(stock)
    },
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4974a5),
            contentColor = Color.White) )
        {
            Text("Add stock to your favourites")
    }


}



