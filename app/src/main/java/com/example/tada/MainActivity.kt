package com.example.tada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tada.ui.theme.TadaTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialNavigationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets {
                TadaTheme {
                    Navigation()
                }
            }
        }
    }
}