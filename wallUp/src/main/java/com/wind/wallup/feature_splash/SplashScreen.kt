package com.wind.wallup.feature_splash

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wind.wallup.R
import com.wind.wallup.destinations.HomeScreenDestination
import com.wind.wallup.destinations.SplashScreenDestination
import com.wind.wallup.utils.UNSPLASH_URL

@Destination(
    start = true
)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    Log.d("TAG", "SplashScreen: $navigator")
    var isStarted by remember { mutableStateOf(false) }
    val animateScale by animateFloatAsState(
        targetValue = if (isStarted) 1.2F else 1F,
        animationSpec = tween(durationMillis = 1500)
    ) {
        navigator.navigate(HomeScreenDestination) {
            popUpTo(SplashScreenDestination.route) {
                inclusive = true
            }
        }
    }
    LaunchedEffect(key1 = true, block = { isStarted = true })
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .scale(animateScale)
                .blur(radius = 25.dp),
            painter = painterResource(id = R.drawable.ic_splash),
            contentScale = ContentScale.Crop,
            contentDescription = "Splash Image"
        )
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 48.sp,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.background,
            modifier = Modifier.align(Alignment.Center)
        )
        val description = buildAnnotatedString {
            append("Photos provided by Unsplash")
            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp
                ),
                start = 19,
                end = 27
            )
            addStringAnnotation(
                tag = "Unsplash",
                annotation = UNSPLASH_URL,
                start = 19,
                end = 27
            )
        }
        Text(
            text = description,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        )
    }
}
