package com.vaibhav.composetextflipanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vaibhav.composetextflipanimation.ui.theme.ComposeTextFlipAnimationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTextFlipAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        RotatingTextList(
                            items = listOf(
                                "Item 1",
                                "Item 2",
                                "Item 3",
                                "Item 4",
                                "Item 5",
                                "Item 6"
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RotatingTextList(
    items: List<String>,
    shouldRunInfinite: Boolean = true,
    isVisible: Boolean = true,
) {
    if (isVisible.not()) return
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentIndex, shouldRunInfinite) {
        delay(1500)
        if (currentIndex < items.size - 1) {
            currentIndex++
        } else if (shouldRunInfinite) {
            currentIndex = 0
        }
    }

    AnimatedContent(
        targetState = items[currentIndex],
        transitionSpec = {
            slideInVertically { fullHeight -> fullHeight } + fadeIn() togetherWith slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
        },
        label = "Text Animation",
    ) { currentItem ->
        Text(
            text = currentItem,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
        )
    }
}
