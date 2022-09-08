package com.example.financeapp.presentation.company_details


import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.domain.model.IntradayInfo
//import com.example.stockappcompose.domain.model.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt

//the composable used to make/display the stock chart dynamically for each chart
@SuppressLint("NewApi")
@Composable
fun StocksChart(
    intradayInfosChart: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Magenta
) {

    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }

    //for the uppervalue of the axis (high $ price of the day)
    val upperValue = remember {
        (intradayInfosChart.maxOfOrNull { it.close }?.plus(1)?.roundToInt()
            ?: 0) //returns max of value at day's end
        //rounded, for easier display
    }

    val lowerValue = remember(intradayInfosChart) {
        (intradayInfosChart.minOfOrNull { it.close }?.toInt() ?: 0)
    }
    val density =
        LocalDensity.current//needed to use sp lol...stupidly complicated just to get a text size
    val textPaintObject = remember(density) {
        //make sure to use android.graphics paint, not the compose version
        Paint().apply {
            color = android.graphics.Color.BLUE
            textAlign = Paint.Align.CENTER
            textSize = density.run {
                12.sp.toPx()
            }
        }
    }
    Canvas(modifier = modifier) {
        //start with "drawing" the txt, the axes
        val spacingPerHour = (size.width - spacing) / intradayInfosChart.size // x axis
        (0 until intradayInfosChart.size - 1 step 2).forEach { i ->
            //what it means is, loop through the infos.size (our hours) and only show every 2 hours (step 2)
            val info = intradayInfosChart[i]
            val hour = info.date.hour
            //x
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacingPerHour,
                    size.height - 4,
                    textPaintObject
                )
            }
            //y
            val spacingStockPrice = (upperValue - lowerValue) /5f//show 5 numbers
            (0..4).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        round(lowerValue + spacingStockPrice * i).toString(),
                        30f,
                        size.height- spacing -i *size.height /5f,
                        textPaintObject
                    )
                }
            }

            var lastXCoord:Float = 0.0F

            //draws a line, a path
            val strokePath = Path().apply {
                val height = size.height
                for (i in intradayInfosChart.indices){
                    val currentStockPrice = intradayInfosChart[i]
                    val nextPrice = intradayInfosChart.getOrNull(i+1)?: intradayInfosChart.last()
                    val leftRatio = (currentStockPrice.close - lowerValue)/(upperValue - lowerValue)// for the intervals
                    val rightRatio = (nextPrice.close - lowerValue) / (upperValue-lowerValue)

                    val x1 = spacing +i *spacingPerHour
                    val y1 = height - spacing - (leftRatio * height).toFloat()
                    val x2 = spacing +(i+1) *spacingPerHour
                    val y2 = height - spacing - (rightRatio * height).toFloat()
                    if(i==0){
                        moveTo(x1,y1)
                    }
                    lastXCoord = (x1+x2)/2f
                    //basically its how its drawn, had to look up how it works
                    quadraticBezierTo(x1,y1,(x1+x2)/2f,(y1+y2)/2f)

                }
            }// end strokepath

            //stupidly need to first convert to android path from compose, then immediately back t ocompose
            val fillPath = android.graphics.Path(strokePath.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(lastXCoord, size.height-spacing)
                    lineTo(spacing, size.height-spacing)
                    close()//close() connects the path
                }
            drawPath(path=fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(transparentGraphColor, Color.Transparent),
                    endY= size.height - spacing)

            )
            drawPath(path=strokePath,
                color=graphColor,
                style = Stroke(width = 3.dp.toPx(),
                    cap= StrokeCap.Round)
            )

        }

    }


}