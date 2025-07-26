package com.spksh.graph.ui

import android.graphics.Color.red
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.spksh.graph.state.AccountAnalysisState
import kotlin.math.absoluteValue
import kotlin.math.max

@Composable
fun AccountAnalysisPlot(
    state: AccountAnalysisState,
    modifier: Modifier = Modifier,
    plusColor: Color = Color.Green,
    minusColor: Color = Color.Red,
    labelTextStyle: TextStyle = MaterialTheme.typography.labelSmall,
    minBarSpacing: Float = 8f,
    labelStep: Int = 5
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        if (state.data.isEmpty()) return@Canvas

        val textLayoutHeight = textMeasurer.measure("", labelTextStyle).size.height
        val sidePadding = 16f
        val bottomPadding = 24f
        val totalItems = state.data.size
        val availableWidth = size.width - sidePadding * 2

        val (barWidth, barSpacing) = calculateBarDimensions(
            totalItems = totalItems,
            availableWidth = availableWidth,
            commonBarSpacing = minBarSpacing,
        )

        val maxBarHeight = size.height * 0.8f - bottomPadding
        val maxValue = state.data.maxOfOrNull { it.height.absoluteValue } ?: 1f
        val heightScale = maxBarHeight / maxValue

        var currentX = sidePadding

        state.data.forEachIndexed { index, model ->
            val barHeight = max(model.height.absoluteValue * heightScale, barWidth)
            val topY = size.height - bottomPadding - barHeight - textLayoutHeight

            drawRoundRect(
                color = if (model.height < 0) minusColor else plusColor,
                topLeft = Offset(currentX, topY),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2)
            )
            if (index % labelStep == 0) {
                val text = model.date
                val textLayout = textMeasurer.measure(text, labelTextStyle)
                val textX = currentX + barWidth / 2 - textLayout.size.width / 2f
                val textY = size.height - bottomPadding - textLayout.size.height
                drawText(
                    textLayoutResult =  textLayout,
                    topLeft = Offset(textX, textY)
                )
            }

            currentX += barWidth + barSpacing
        }
    }
}

private fun DrawScope.calculateBarDimensions(
    totalItems: Int,
    availableWidth: Float,
    commonBarSpacing: Float
): Pair<Float, Float> {
    val barWidth = (availableWidth - (totalItems - 1) * commonBarSpacing) / totalItems
    if (barWidth < commonBarSpacing) {
        val width = availableWidth / (totalItems + totalItems - 1)
        return width to width
    } else {
        return barWidth to commonBarSpacing
    }
}