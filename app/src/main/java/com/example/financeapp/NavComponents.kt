package com.example.financeapp


import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.ui.Screen
import com.example.financeapp.ui.TopNavHost
import com.example.financeapp.ui.TopNavScreen

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
        verticalArrangement = Arrangement.spacedBy(12.dp),
        Alignment.CenterHorizontally
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

        val radioOptions = listOf("Daily", "Weekly", "Monthly", "Quarterly","Yearly")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[3]) }
        var compoundPeriod = 0
        if (selectedOption.equals("Daily"))
            compoundPeriod = 365
        else if (selectedOption.equals("Weekly"))
            compoundPeriod= 52
        else if (selectedOption.equals("Monthly"))
            compoundPeriod = 12
        else if (selectedOption.equals("Quarterly"))
            compoundPeriod = 4
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

                            onClick = { onOptionSelected(text) }
                        )


                ) {

                    //generate Radio buttons
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {onOptionSelected(text)
                        })

                    Text(
                        text = text,
                        fontSize = 12.sp
                        //modifier = Modifier.padding(start = 16.dp)
                    )
                }}}
        Text(text = "Your Projected total is: ")
        var total = remember { mutableStateOf(0.0) }

        Text(text= "$"+"%.2f".format(total.value), fontSize = 28.sp)
        Button(
            onClick = {
                var principal: Int
                var years: Int
                var rates: Double
                //var compound: Int

                principal = Integer.parseInt(principalState.value.text)
                years = Integer.parseInt(yearState.value.text)
                rates = interestState.value.text.toDouble()
                //compound = Integer.parseInt(selectedOption)


                //*** calculation***
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




@Preview
@Composable
fun LearningPage(){
    val listItems = listOf(Screen.History2, Screen.Basics, Screen.Types, Screen.Advanced)//
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Stars")
        Scaffold(topBar = {TopNavScreen(navController = navController, item = listItems)
        }) {
            TopNavHost(topNavHostController = navController)
        }
    }

}

@Composable
fun History(){
        Column(Modifier.verticalScroll(rememberScrollState())
            ){
            Text(text= stringResource(R.string.History1), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
            Image(painter = painterResource(id = R.drawable.amsterdam), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
            Text(text = stringResource(id = R.string.History2), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        }
}

@Composable
fun Basics(){
    Column(Modifier.verticalScroll(rememberScrollState())){
        Text(text= stringResource(R.string.Basics1), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.stockbond), contentDescription =null , Modifier.align(Alignment.CenterHorizontally), contentScale = ContentScale.Fit)
        Text(text = stringResource(id = R.string.Basics2), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.diversification), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(text = stringResource(id = R.string.Basics3), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)

    }
}
@Composable
fun Types(){
    Column(Modifier.verticalScroll(rememberScrollState())){
        Text(text= stringResource(R.string.Types1), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.bonds), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(text = stringResource(id = R.string.Types2), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.indexfund), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(text = stringResource(id = R.string.Types3), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)

    }
}
@Composable
fun Advanced(){
    Column(Modifier.verticalScroll(rememberScrollState())){
        Text(text= stringResource(R.string.More1), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.leverage), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(text = stringResource(id = R.string.More2), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
        Image(painter = painterResource(id = R.drawable.margin), contentDescription =null , Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(text = stringResource(id = R.string.More3), fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)

    }
}