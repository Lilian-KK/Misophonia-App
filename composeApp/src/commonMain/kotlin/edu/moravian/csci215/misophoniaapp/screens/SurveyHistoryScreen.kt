package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.moravian.csci215.misophoniaapp.formatEpochMillis
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyType
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.date
import misophoniaapp.composeapp.generated.resources.history
import misophoniaapp.composeapp.generated.resources.no_history
import misophoniaapp.composeapp.generated.resources.score
import misophoniaapp.composeapp.generated.resources.survey_type
import org.jetbrains.compose.resources.stringResource

@Serializable
data object SurveyHistory

@Composable
fun SurveyHistoryScreen(
    repository: SurveyRepository,
    onViewPastSurvey: (Long, SurveyType) -> Unit
) {
    val entriesState = repository.allResults.collectAsState(emptyList())
    val entries = entriesState.value

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(Res.string.history), style = MaterialTheme.typography.headlineSmall)

        if (entries.isEmpty()) {
            Text(stringResource(Res.string.no_history))
            return
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(entries, key = { it.id }) { result ->
                Card(modifier = Modifier.fillMaxWidth().clickable { onViewPastSurvey(result.id, result.surveyType) }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(stringResource(Res.string.survey_type, result.surveyType))
                        Text(stringResource(Res.string.date, formatEpochMillis(result.completedAtEpochMillis)))
                        Text(stringResource(Res.string.score, result.totalScore))
                    }
                }
            }
        }
    }
}