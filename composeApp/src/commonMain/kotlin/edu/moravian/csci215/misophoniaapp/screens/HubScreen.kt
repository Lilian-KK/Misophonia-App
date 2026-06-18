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
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.amisosr_survey
import misophoniaapp.composeapp.generated.resources.duke_survey
import misophoniaapp.composeapp.generated.resources.trigger_log
import org.jetbrains.compose.resources.stringResource

@Serializable
data object Hub

@Composable
fun HubScreen(onSurveySelected: (SurveyType) -> Unit) {
    Column {
        Text("This is the hub, which will show when setup = true.")
        Button(onClick = { onSurveySelected(SurveyType.AMISOSR_SURVEY) }) {
            Text(stringResource(Res.string.amisosr_survey))
        }
        Button(onClick = { onSurveySelected(SurveyType.DUKE_SURVEY) }) {
            Text(stringResource(Res.string.duke_survey))
        }
        Button(onClick = { onSurveySelected(SurveyType.TRIGGER_LOG_SURVEY) }) {
            Text(stringResource(Res.string.trigger_log))
        }
    }
}
