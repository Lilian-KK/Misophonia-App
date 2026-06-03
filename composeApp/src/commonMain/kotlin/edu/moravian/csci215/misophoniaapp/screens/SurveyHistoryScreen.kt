package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import kotlinx.serialization.Serializable

@Serializable
data object SurveyHistory

@Composable
fun SurveyHistoryScreen(
    repository: SurveyRepository,
    onViewPastSurvey: () -> Unit,
) {
    Column {
        Text("This is SurveyHistoryScreen.")
        Button(onClick = onViewPastSurvey) {
            Text("to a past survey!")
        }
    }
}
