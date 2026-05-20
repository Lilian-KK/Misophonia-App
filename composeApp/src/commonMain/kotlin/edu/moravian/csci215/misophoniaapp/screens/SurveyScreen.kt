package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object Survey

@Composable
fun SurveyScreen(
    onSubmission: () -> Unit
) {
    Column() {
        Text("This is Survey Screen")
        Button(onClick = onSubmission ) {
            Text("to trigger history logs!")
        }
    }
}