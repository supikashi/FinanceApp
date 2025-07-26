package com.spksh.graph.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.spksh.graph.state.TransactionsAnalysisState
import com.spksh.graph.utils.plotColors

@Composable
fun TransactionsAnalysisPlot(
    state: TransactionsAnalysisState,
    modifier: Modifier = Modifier
) {

    val stroke = with(LocalDensity.current) { Stroke(8.dp.toPx()) }

    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(Modifier.fillMaxHeight().weight(1f)) {
            val innerRadius = (size.minDimension - stroke.width) / 2
            val halfSize = size / 2.0f
            val topLeft = Offset(
                halfSize.width - innerRadius,
                halfSize.height - innerRadius
            )
            val size = Size(innerRadius * 2, innerRadius * 2)
            var startAngle = -120f
            state.data.forEach { item ->
                val sweep = item.proportion * 360f
                drawArc(
                    color = plotColors.getOrElse(item.id.toInt() - 1, {Color.Green}),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    topLeft = topLeft,
                    size = size,
                    useCenter = false,
                    style = stroke
                )
                startAngle += sweep
            }
        }
        Column(Modifier.weight(1f)) {
            state.data.forEach {
                PlotText(it.text, plotColors.getOrElse(it.id.toInt() - 1 , {Color.Green}),)
            }
        }
    }
}

@Composable
private fun PlotText(
    text: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}