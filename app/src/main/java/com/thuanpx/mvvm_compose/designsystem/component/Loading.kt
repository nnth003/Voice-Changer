package com.thuanpx.mvvm_compose.designsystem.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thuanpx.mvvm_compose.designsystem.theme.AppThemePreview
import com.thuanpx.mvvm_compose.designsystem.theme.Colors

/**
 * Created by ThuanPx on 12/10/2023.
 */
@Composable
fun AppLoading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.width(36.dp),
        color = Colors.Primary,
        trackColor = Color(0xFF000000).copy(alpha = 0.1f),
        strokeCap = StrokeCap.Round
    )
}

@Preview
@Composable
private fun AppLoadingPreview() {
    AppThemePreview {
        AppLoading()
    }
}
