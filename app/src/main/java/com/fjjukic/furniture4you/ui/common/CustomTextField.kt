package com.fjjukic.furniture4you.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField("", {}, "Placeholder")
}

@Composable
fun CustomTextField(
    fieldValue: String,
    onTextChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
) {
    BasicTextField(
        value = fieldValue.uppercase(),
        onValueChange = {
            if (it.length <= 20) {
                onTextChanged(it)
            }
        },
        modifier = modifier
            .background(
                Color.White,
                RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 20.dp)
            .height(44.dp)
            .fillMaxWidth(),
        singleLine = singleLine,
        textStyle = LocalTextStyle.current.copy(
            fontFamily = nunitoSansFamily,
            fontSize = 16.sp
        ),
        interactionSource = remember { MutableInteractionSource() }
    ) { innerTextField ->
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            if (fieldValue.isEmpty()) {
                Text(
                    placeholder,
                    style = LocalTextStyle.current.copy(
                        color = Color.Black.copy(alpha = 0.3f),
                        fontSize = 16.sp
                    )
                )
            }
            innerTextField()
        }
    }
}