package com.example.financeapp

import com.example.financeapp.ui.Screen
import org.junit.Assert.*
import org.junit.Test

class CalculateTest {

//testing the calculator's math
    @Test
    fun isCorrect(){
        var finalAmt =0.0
        val principal: Double = 500.0
        val compoundPeriod = 1 // annual
        val rates = 10.0 //10%
        val years = 1
        //var total = 0

        finalAmt = principal *(Math.pow((1+((rates/100)/compoundPeriod)),
            (compoundPeriod*years).toDouble()))

        //should be 540.0

        assertEquals(550.0, finalAmt, 0.1)//delta of 0.1 to allow for rounding errors
    }
}