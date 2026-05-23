package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.formatEpochMillis
import edu.moravian.csci215.misophoniaapp.survey_data.Render
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyResultEntity
import edu.moravian.csci215.misophoniaapp.survey_data.load
import edu.moravian.survey.AMISOS_R_SURVEY
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.loading
import misophoniaapp.composeapp.generated.resources.survey_results_from
import org.jetbrains.compose.resources.stringResource

/**
 * Navigation destination for the screen that shows a survey that has already been taken. The survey
 * id must be provided.
 */
@Serializable
data class ViewSurvey(
    val surveyId: Long
)

/**
 * Displays a survey that has already been taken. The survey is not editable, but it shows the
 * answers that were given.
 */
@Composable
fun ViewSurveyScreen(
    surveyId: Long,
    repository: SurveyRepository,
) {
    var loading by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<SurveyResultEntity?>(null) }
    var survey by remember { mutableStateOf(AMISOS_R_SURVEY) }
    LaunchedEffect(surveyId) {
        result = repository.getResult(surveyId).first()
        survey = survey.load(surveyId, repository)
        loading = false
    }

    if (loading) {
        Row {
            CircularProgressIndicator()
            Text(stringResource(Res.string.loading))
        }
        return
    }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            stringResource(Res.string.survey_results_from, formatEpochMillis(result!!.completedAtEpochMillis)),
            style = MaterialTheme.typography.bodyLarge,
        )
        survey.Render(
            Modifier
                .weight(1f)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.shapes.medium,
                ).padding(10.dp)
                .fillMaxWidth(),
            false,
            null,
        )
    }
}