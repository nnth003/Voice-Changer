package com.pixelzlab.app.core

import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi preview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */

@Preview(name = "Phone", device = "spec:width=360dp,height=640dp,dpi=480")
annotation class PhoneDevicePreviews

@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=480")
annotation class TabletDevicePreviews

@Preview(name = "Landscape", device = "spec:width=640dp,height=360dp,dpi=480")
annotation class LandscapeDevicePreviews

@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp,dpi=480")
annotation class FoldableDevicePreviews

@PhoneDevicePreviews
@TabletDevicePreviews
@LandscapeDevicePreviews
@FoldableDevicePreviews
annotation class DevicePreviews