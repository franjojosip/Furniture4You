package com.fjjukic.furniture4you.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ht.ferit.fjjukic.foodlovers.R


@Composable
fun ProductDetailCartDialog(
    onContinueClick: () -> Unit,
    onDismissClick: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true
) {
    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        ProductDetailDialog(
            onDismissClick = onDismissClick,
            onContinueClick = onContinueClick
        )
    }
}

@Composable
fun ProductDetailDialog(
    onDismissClick: () -> Unit,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier.background(colorResource(id = R.color.color_white))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = colorResource(id = R.color.bg_dialog_icon)
                ),
                modifier = Modifier
                    .padding(top = 30.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.title_add_to_cart),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.subtitle_add_to_cart),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = onDismissClick) {

                    Text(
                        stringResource(id = R.string.btn_open_cart),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_gray),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
                TextButton(onClick = onContinueClick) {
                    Text(
                        stringResource(id = R.string.btn_continue_shopping),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.color_black),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MyDialogUIPreview() {
    ProductDetailDialog(onContinueClick = {}, onDismissClick = {})
}
