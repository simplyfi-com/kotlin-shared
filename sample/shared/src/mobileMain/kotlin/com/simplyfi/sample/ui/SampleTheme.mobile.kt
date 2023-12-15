package com.simplyfi.sample.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
actual fun SampleTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}