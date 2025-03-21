package com.example.aroundegypt.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.aroundegypt.R
import com.example.aroundegypt.data.model.Experience

@Composable
fun ItemCard(
    experience: Experience,
    onLike: (String) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
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

@Composable
fun LoadNetworkImage(imageUrl: String?,modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Experience Image",
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.ic_fallback_image),
        placeholder = painterResource(id =  R.drawable.ic_fallback_image),
        modifier = modifier
    )
}

@Composable
private fun RecommendedBadge(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(start = 8.dp, top = 8.dp)
            .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(20.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star Icon",
            tint = Color(0xFFFFA726),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "RECOMMENDED",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
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
private fun ViewsCount(views: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Visibility, contentDescription = "Views", tint = Color.White)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = views.toString(), color = Color.White, fontSize = 14.sp)
    }
}

@Composable
private fun ImagesIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Outlined.Collections,
        contentDescription = "Images",
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



@Composable
private fun LikeButton(likes: Int, onLike: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = likes.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Like",
            modifier = Modifier
                .size(20.dp)
                .clickable { onLike() },
            tint = Color(0xFFF18757)
        )
    }
}
