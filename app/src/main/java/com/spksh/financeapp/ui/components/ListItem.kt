package com.spksh.financeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spksh.financeapp.R

@Composable
fun ListItem(
    leadIcon: String? = null,
    showTrailIcon: Boolean = true,
    minHeight: Dp = 72.dp,
    containerColor: Color = MaterialTheme.colorScheme.background,
    emojiContainerColor: Color = MaterialTheme.colorScheme.secondary,
    isClickable: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val modifier = if (isClickable) {
        Modifier.clickable { onClick() }
    } else {
        Modifier
    }
    Row(
        modifier = modifier
            .heightIn(min = minHeight)
            .fillMaxWidth()
            .background(color = containerColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))

        leadIcon?.let {
            Box(
                modifier = Modifier
                    .background(emojiContainerColor, CircleShape)
                    .sizeIn(minWidth = 24.dp, minHeight = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    maxLines = 1,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        if (showTrailIcon) {
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.more_vert),
                contentDescription =  "Get detailed info",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(Modifier.width(14.dp))
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(
        leadIcon = "RR",
        showTrailIcon = true
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Text text text")
            Text("Text text text")
        }
    }
}