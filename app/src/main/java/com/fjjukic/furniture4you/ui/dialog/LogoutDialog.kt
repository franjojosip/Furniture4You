package com.fjjukic.furniture4you.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R


@Composable
fun LogoutDialog(
    onConfirmClick: () -> Unit,
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
        LogoutDialogContent(
            onConfirmClick = onConfirmClick,
            onDismissClick = onDismissClick
        )
    }
}

@Preview
@Composable
fun LogoutDialogPreview() {
    LogoutDialogContent(
        onConfirmClick = {},
        onDismissClick = {}
    )
}

@Composable
fun LogoutDialogContent(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = modifier.background(colorResource(id = R.color.color_white))
        ) {
            Text(
                text = stringResource(R.string.title_logout),
                fontSize = 18.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.color_medium_gray),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 12.dp),
            )

            HorizontalDivider(
                color = colorResource(id = R.color.color_tinted_white),
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Text(
                text = stringResource(R.string.label_logout),
                fontSize = 16.sp,
                fontFamily = gelatioFamily,
                color = colorResource(id = R.color.color_medium_gray),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDismissClick) {
                    Text(
                        text = stringResource(id = R.string.btn_no),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_gray),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(
                    onClick = { onConfirmClick() }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_yes),
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.color_black),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}