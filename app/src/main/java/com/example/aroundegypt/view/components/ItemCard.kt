package com.example.aroundegypt.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aroundegypt.R
import com.example.aroundegypt.data.model.Experience

@Composable
fun ItemCard(
    experience: Experience,
    onLike: (String) -> Unit,
    modifier: Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .width(screenWidth * 0.9f)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            LoadNetworkImage(
                imageUrl = experience.cover_photo,
                modifier = Modifier.height(180.dp)
            )

            if (experience.recommended == 1) {
                RecommendedBadge(modifier = Modifier.align(Alignment.TopStart))
            }

            _360Icon(modifier = Modifier.align(Alignment.Center))

            InfoIcon(modifier = Modifier.align(Alignment.TopEnd))

            ViewsCount(experience.views_no, Modifier.align(Alignment.BottomStart))

            ImagesIcon(modifier = Modifier.align(Alignment.BottomEnd))
        }

        ExperienceFooter(experience, onLike)
    }
}

@Composable fun _360Icon(modifier: Modifier = Modifier){
    Icon(
        painter = painterResource(id = R.drawable.ic_360_degree),
        contentDescription = "360 View",
        tint = Color.White,
        modifier = modifier
            .size(48.dp)
    )
}

@Composable
private fun InfoIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Outlined.Info,
        contentDescription = "Info",
        tint = Color.White,
        modifier = modifier.padding(6.dp)
    )
}

@Composable
fun ExperienceFooter(experience: Experience, onLike: (String) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxTitleWidth = screenWidth * 0.45f

    val textMeasurer = rememberTextMeasurer()
    val textWidth = textMeasurer.measure(
        text = experience.title,
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
    ).size.width.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (textWidth < maxTitleWidth) {
                Text(
                    text = experience.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = experience.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            LikeButton(likes = experience.likes_no, onLike = { onLike(experience.id) } )
        }
    }
}

