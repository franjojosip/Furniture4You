package com.fjjukic.furniture4you.ui.common.fields

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily

@Preview
@Composable
fun PromoCodeFieldPreview() {
    PromoCodeField(
        fieldValue = "",
        onTextChange = {},
        placeholder = stringResource(id = R.string.placeholder_enter_promo_code)
    )
}

@Composable
fun PromoCodeField(
    fieldValue: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    isEnabled: Boolean = true
) {
    BasicTextField(
        value = fieldValue.uppercase(),
        onValueChange = {
            if (it.length <= 20) {
                onTextChange(it)
            }
        },
        modifier = modifier
            .background(
                colorResource(id = R.color.color_white),
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
        enabled = isEnabled,
        visualTransformation = PromoCodeFieldTransformation(),
        interactionSource = remember { MutableInteractionSource() }
    ) { innerTextField ->
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            if (fieldValue.isEmpty()) {
                Text(
                    placeholder,
                    style = LocalTextStyle.current.copy(
                        color = colorResource(id = R.color.color_black).copy(alpha = 0.3f),
                        fontSize = 16.sp
                    )
                )
            }
            innerTextField()
        }
    }
}

class PromoCodeFieldTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val isBlank = text.text.isBlank()
        val transformedText = if (isBlank) text.text else "#${text.text}"

        val promoCodeOffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (isBlank) {
                    true -> 0
                    else -> text.text.length + 1
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (isBlank) {
                    true -> 0
                    else -> text.text.length
                }
            }
        }
        return TransformedText(AnnotatedString(transformedText), promoCodeOffsetMapping)
    }
}