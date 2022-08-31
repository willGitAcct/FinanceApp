package com.example.financeapp


import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.ui.Screen

@Composable
fun HomePage(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Home")
    }
}

@Composable
fun CalculatorPage(){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(text = "Compound Interest Calculator!\nWatch how your investment can grow.")

        //principal
        val principalState = remember{ mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = principalState.value,
            onValueChange = {principalState.value = it},
            label = {Text(text= "Initial Amount")},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        //years
        val yearState = remember{ mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = yearState.value,
            onValueChange = {yearState.value = it},
            label = {Text(text= "Investment Years")},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

        //interest rate
        val interestState = remember{ mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = interestState.value,
            onValueChange = {interestState.value = it},
            label = {Text(text= "Estimated Interest Rate")},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),)

        Text("Compound Frequency")

        val radioOptions = listOf("Daily", "Weekly", "Monthly", "Yearly")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[3]) }
        var compoundPeriod = 0
        if (selectedOption.equals("Daily"))
            compoundPeriod = 365
        else if (selectedOption.equals("Weekly"))
            compoundPeriod= 52
        else if (selectedOption.equals("Monthly"))
            compoundPeriod = 12
        else
            compoundPeriod = 1

        var finalAmt: Double = 0.0

        Row {
            // below line is use to set data to
            // each radio button in columns.
            radioOptions.forEach { text ->
                Column(
                    Modifier
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == selectedOption),
                            // below method is called on
                            // clicking of radio button.
                            onClick = { onOptionSelected(text) }
                        )


                ) {

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == selectedOption),

                        onClick = {
                            // inside on click method we are setting a
                            // selected option of our radio buttons.
                            onOptionSelected(text)

                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        fontSize = 12.sp
                        //modifier = Modifier.padding(start = 16.dp)
                    )
                }}}
        Text(text = "Your Projected total is: ")
        var total = remember { mutableStateOf(0.0) }

        Text("$"+"%.2f".format(total.value))
        Button(
            onClick = {
                var principal: Int
                var years: Int
                var rates: Double
                //var compound: Int

                principal = Integer.parseInt(principalState.value.text)
                years = Integer.parseInt(yearState.value.text)
                rates = Integer.parseInt(interestState.value.text).toDouble()
                //compound = Integer.parseInt(selectedOption)


                finalAmt = principal *(Math.pow((1+((rates/100)/compoundPeriod)),
                    (compoundPeriod*years).toDouble()))
                total.value = finalAmt
                println("%.2f".format(finalAmt))
                println(rates)
                println(years)
                println(principal)
                println(compoundPeriod)


            },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color( 0xFF4974a5),
                contentColor = Color.White
            ) ) {
            Text(
                text = "Calculate"
            )

        }


    }//end col




}//end calc





@Composable
fun LearningPage(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Academy")
        Column {
            Button(onClick = {},
                modifier = Modifier.fillMaxWidth().padding(12.dp)
                    .height(120.dp)
                    .background(Color.Transparent),
                shape = RoundedCornerShape(8.dp),
                //contentPadding = PaddingValues(12.dp)

            ){
                Image(painter = painterResource(id = R.drawable.history), contentDescription ="History"
                , contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize())
            }

            Button(onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
                ){
                Image(painter = painterResource(id = R.drawable.investbasics), contentDescription ="History" )
            }

            Button(onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)


            ){
                Image(painter = painterResource(id = R.drawable.types), contentDescription ="History" )
            }

            Button(onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)


            ){
                Image(painter = painterResource(id = R.drawable.inter), contentDescription ="History" )
            }

        }
    }
}