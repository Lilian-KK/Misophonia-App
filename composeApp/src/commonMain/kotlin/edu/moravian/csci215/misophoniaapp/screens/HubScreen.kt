package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.moravian.csci215.misophoniaapp.survey_data.Survey
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyType
import edu.moravian.csci215.misophoniaapp.surveys.AMISOS_R_SURVEY
import edu.moravian.csci215.misophoniaapp.surveys.DUKE_SURVEY
import edu.moravian.csci215.misophoniaapp.surveys.TRIGGER_LOG_SURVEY
import kotlinx.serialization.Serializable

@Serializable
data object Hub

@Composable
fun HubScreen(onSurveySelected: (SurveyType) -> Unit) {
    Column {
        Text("This is the hub, which will show when setup = true.")
        Button(onClick = { onSurveySelected(SurveyType.AMISOSR_SURVEY) }) {
            Text("AMISOSR Survey")
        }
        Button(onClick = { onSurveySelected(SurveyType.DUKE_SURVEY) }) {
            Text("Duke Survey")
        }
        Button(onClick = { onSurveySelected(SurveyType.TRIGGER_LOG_SURVEY) }) {
            Text("Log Trigger")
        }
    }
}
