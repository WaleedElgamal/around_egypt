package com.example.aroundegypt.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.aroundegypt.R

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
fun ViewsCount(views: Int, modifier: Modifier = Modifier) {
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
fun ShareIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Outlined.IosShare,
        contentDescription = "Share",
        tint = Color(0xFFF18757),
        modifier = modifier.padding(6.dp)
    )
}


@Composable
fun ImagesIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Outlined.Collections,
        contentDescription = "Images",
        tint = Color.White,
        modifier = modifier.padding(6.dp)
    )
}

@Composable
fun LikeButton(likes: Int, onLike: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = likes.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Like",
            modifier = Modifier
                .clickable { onLike() },
            tint = Color(0xFFF18757)
        )
    }
}

@Composable
fun RecommendedBadge(modifier: Modifier = Modifier) {
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