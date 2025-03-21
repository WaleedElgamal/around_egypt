package com.example.aroundegypt.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleHeader(title: String){
    Text(
        text = title,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(14.dp)
    )
}

@Composable
fun DescriptionHeader(){
    Text(
        text = "Now you can explore any experience in 360 degrees and get all the details about it all in one place.",
        color = Color.Black,
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}