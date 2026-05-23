package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object SurveyHistory

@Composable
fun SurveyHistoryScreen(
    onViewPastSurvey: () -> Unit
) {
    Column() {
        Text("This is SurveyHistoryScreen.")
        Button(onClick = onViewPastSurvey ) {
            Text("to a past survey!")
        }
    }
}