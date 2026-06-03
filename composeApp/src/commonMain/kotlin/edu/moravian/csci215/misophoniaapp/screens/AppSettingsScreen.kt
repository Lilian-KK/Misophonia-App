package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object AppSettings

@Composable
fun AppSettingsScreen() {
    Column {
        Text("This is the app settings screen.")
    }
}
