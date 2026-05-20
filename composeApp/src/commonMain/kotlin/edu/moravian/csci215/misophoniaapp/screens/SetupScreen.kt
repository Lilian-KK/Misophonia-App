package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object Setup

@Composable
fun SetupScreen(
    onSetup: () -> Unit
) {
    Column() {
        Text("This is the initial setup screen.")
        Button(onClick = onSetup ) {
            Text("to the hub!")
        }
    }
}