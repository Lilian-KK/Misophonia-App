package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object Hub

@Composable
fun HubScreen(
    onSurveySelected: () -> Unit
) {
    Column() {
        Text("This is the hub, which will show when setup = true.")
        Button(onClick = onSurveySelected ) {
            Text("to Survey!")
        }
    }
}