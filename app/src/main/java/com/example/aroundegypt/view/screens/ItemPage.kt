package com.example.aroundegypt.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.view.components.ImagesIcon
import com.example.aroundegypt.view.components.LikeButton
import com.example.aroundegypt.view.components.LoadNetworkImage
import com.example.aroundegypt.view.components.RecommendedBadge
import com.example.aroundegypt.view.components.ShareIcon
import com.example.aroundegypt.view.components.ViewsCount

@Composable
fun ItemDialog(
    experience: Experience,
    onClickOutside: () -> Unit,
    onLike: (String) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Dialog(
        onDismissRequest = onClickOutside,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        ) {
            Column (modifier = Modifier.background(Color.White)) {
                ImageSection(experience = experience, screenHeight = screenHeight)
                Spacer(modifier = Modifier.height(8.dp))
                TitleSection(experience = experience, onLike = onLike)
                HorizontalDivider(
                    modifier = Modifier
                        .width(screenWidth * 0.9f)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    color = Color(0xFFDADADA)
                )
                DescriptionSection(experience = experience)
            }
        }
    }
}

@Composable
fun ImageSection(experience: Experience, screenHeight: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.3f)
    ) {
        LoadNetworkImage(imageUrl = experience.cover_photo, modifier = Modifier.fillMaxSize())
        if (experience.recommended == 1) {
            RecommendedBadge(modifier = Modifier.align(Alignment.TopStart))
        }
        ExploreButton(modifier = Modifier.align(Alignment.Center))
        ViewsCount(experience.views_no, modifier = Modifier.align(Alignment.BottomStart))
        ImagesIcon(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun ExploreButton(modifier: Modifier) {
    TextButton(
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF5722), containerColor = Color.White),
        modifier = modifier.padding(12.dp)
    ) {
        Text(text = "EXPLORE NOW", color = Color(0xFFFF5722))
    }
}

@Composable
fun TitleSection(experience: Experience, onLike: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = experience.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShareIcon()
                Spacer(modifier = Modifier.width(4.dp))
                LikeButton(likes = experience.likes_no, onLike = { onLike(experience.id) })
            }
        }
        Text(text = "${experience.city["name"]}, Egypt.", fontSize = 15.sp)
    }
}

@Composable
fun DescriptionSection(experience: Experience) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
        Text(text = "Description", fontWeight = FontWeight.Bold, fontSize = 22.sp,
            modifier = Modifier.semantics { contentDescription = "Description Header" })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = experience.description,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.semantics { contentDescription = "Experience Description" }
        )
    }
}
