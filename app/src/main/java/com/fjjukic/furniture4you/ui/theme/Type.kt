package com.fjjukic.furniture4you.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Custom typography
val NunitoSansTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = gelatioFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = gelatioFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = nunitoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunitoSansFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    ),
    labelLarge = TextStyle(
        fontFamily = nunitoSansFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
)

val GelatioTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = gelatioFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = nunitoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = gelatioFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    labelSmall = TextStyle(
        fontFamily = gelatioFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    ),
    labelLarge = TextStyle(
        fontFamily = gelatioFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
)