package com.spksh.financeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spksh.financeapp.R

@Composable
fun TopBar(
    headline: String = "",
    leftIcon: Int? = null,
    rightIcon: Int? = null,
    leftIconOnClick: () -> Unit = {},
    rightIconOnClick: () -> Unit = {},
    leftIconContentDescription: String? = null,
    rightIconContentDescription: String? = null
) {
    val size = 48
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leftIcon != null) {
            IconButton(
                onClick = leftIconOnClick,
                modifier = Modifier.size(size.dp)
            ) {
                Icon(painterResource(leftIcon), leftIconContentDescription)
            }
        } else {
            Spacer(Modifier.size(size.dp))
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = headline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (rightIcon != null) {
            IconButton(
                onClick = rightIconOnClick,
                modifier = Modifier.size(size.dp)
            ) {
                Icon(painterResource(rightIcon), rightIconContentDescription, tint = MaterialTheme.colorScheme.outline)
            }
        } else {
            Spacer(Modifier.size(size.dp))
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar("HeadLine", R.drawable.update)
}