@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.classops

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AutoSizeBox(
    designWidth: Dp = 375.dp,
    designHeight: Dp = 640.dp,
    baseOnWidth: Boolean = true,
    useSystemFontScale: Boolean = false,
    content: @Composable () -> Unit
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width
    val screenHeight = LocalWindowInfo.current.containerSize.height
    val density = LocalDensity.current.density

    // 设置缩放比
    val adaptDensity = if (baseOnWidth) {
        screenWidth / designWidth.value
    } else {
        screenHeight / designHeight.value
    }
    // 设置字体缩放
    val fontScale = if (useSystemFontScale) {
        LocalDensity.current.fontScale
    } else {
        1f
    }
    val adaptFontScale = adaptDensity / density * fontScale

    LaunchedEffect(Unit) {
        println("AutoSizeBox, screen width: $screenWidth, height: $screenHeight")
        println("AutoSizeBox original density: $density, adapt density: $adaptDensity")
        println("AutoSizeBox original fontScale: $density, adapt fontScale: $adaptFontScale")
    }

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = adaptDensity,
            fontScale = adaptFontScale
        ),
        content = content
    )
}