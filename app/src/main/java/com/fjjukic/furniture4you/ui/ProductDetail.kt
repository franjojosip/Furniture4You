package com.fjjukic.furniture4you.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjjukic.furniture4you.ui.components.ColorPalette
import com.fjjukic.furniture4you.ui.components.ShoppingCounter
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import ht.ferit.fjjukic.foodlovers.R

@Preview
@Composable
fun ProductPreviewScreenPreview() {
    ProductDetail()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail() {
    var counter by remember { mutableIntStateOf(1) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
        ) {
            item {
                Box {
                    Image(
                        painter = painterResource(R.drawable.img_minimal_stand),
                        contentDescription = stringResource(R.string.content_desc_launcher_icon),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 8.dp))
                            .padding(start = 48.dp)
                    )
                    Up {}
                    ColorPalette(
                        modifier = Modifier.padding(top = 105.dp, start = 20.dp),
                        colors = listOf(Color.White, Color(0xFFB4916C), Color(0xFFE4CBAD))
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.White)
                ) {
                    Column(
                    ) {
                        Text(
                            text = "Minimal Stand",
                            fontSize = 24.sp,
                            fontFamily = gelatioFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF303030),
                        )
                        Row(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "$ 50",
                                fontSize = 30.sp,
                                fontFamily = gelatioFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF303030),
                            )

                            ShoppingCounter(Modifier, counter, {
                                counter++
                            }, {
                                if (counter > 1) {
                                    counter--
                                }
                            })
                        }

                        Row(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = stringResource(R.string.content_desc_launcher_icon),
                                modifier = Modifier
                                    .size(20.dp),
                                colorFilter = ColorFilter.tint(Color(0xFFF2C94C))
                            )
                            Text(
                                text = "4.5",
                                fontSize = 18.sp,
                                fontFamily = gelatioFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF303030),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Text(
                                text = "(50 reviews)",
                                fontSize = 18.sp,
                                fontFamily = gelatioFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF808080),
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                        Text(
                            text = "Minimal Stand is made of by natural wood. The design that is very simple and minimal." +
                                    "This is truly one of the best furnitures in any family for now." +
                                    "With 3 different colors, you can easily select the best match for your home. ",
                            fontSize = 14.sp,
                            fontFamily = gelatioFamily,
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF606060),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Row {
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF0F0F0))
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_favorite),
                                    tint = Color.Black,
                                    contentDescription = ""
                                )
                            }
                            Button(
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF242424)),
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 16.dp)
                                    .height(60.dp)
                                    .fillMaxWidth(),
                                onClick = {}
                            ) {
                                Text(
                                    text = "Add to cart",
                                    fontSize = 20.sp,
                                    fontFamily = gelatioFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFFFFFFF)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(start = 24.dp, top = 10.dp),
        shape = RoundedCornerShape(6.dp),
        shadowElevation = 8.dp
    ) {
        IconButton(
            onClick = upPress,
            modifier = Modifier
                .padding(10.dp)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = ""
            )
        }
    }
}