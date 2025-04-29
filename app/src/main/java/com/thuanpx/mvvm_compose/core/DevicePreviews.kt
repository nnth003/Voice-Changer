package com.thuanpx.mvvm_compose.core

import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi preview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */

@Preview(name = "Phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
annotation class PhoneDevicePreviews

@Preview(name = "Tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class TabletDevicePreviews

@Preview(name = "Landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
annotation class LandscapeDevicePreviews

@Preview(name = "Foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
annotation class FoldableDevicePreviews

@PhoneDevicePreviews
@TabletDevicePreviews
@LandscapeDevicePreviews
@FoldableDevicePreviews
annotation class DevicePreviews