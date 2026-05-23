package edu.moravian.csci215.misophoniaapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithMultiOptions
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithMultiOptionsAndOther
import edu.moravian.csci215.misophoniaapp.survey_data.Render
import edu.moravian.csci215.misophoniaapp.survey_data.Survey
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyVM
import edu.moravian.csci215.misophoniaapp.survey_data.questions
import edu.moravian.csci215.misophoniaapp.survey_data.update
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.submit
import org.jetbrains.compose.resources.stringResource

@Serializable
data object SurveyScreen

/**
 * Displays the survey screen, which consists of a column with the survey view and a submit button.
 */
@Composable
fun SurveyScreen(
    repository: SurveyRepository,
    vm: SurveyVM = viewModel { SurveyVM(repository) },
    onSubmission: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val saving by vm.saving.collectAsState()
    val errorText by vm.errorText.collectAsState()
    val survey by vm.survey.collectAsState()
    LaunchedEffect(Unit) { loadInitialAnswers(survey, repository, vm) }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        SurveyView(survey, errorText != null, vm::update)

        errorText?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = { scope.launch { vm.save(onSubmission) } },
            enabled = !saving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (saving) {
                CircularProgressIndicator(modifier = Modifier.height(18.dp))
            } else {
                Text(stringResource(Res.string.submit))
            }
        }
    }
}

/**
 * Displays the given survey in a scrollable column. The survey will be rendered using the
 * [Survey.Render] function, and the column will have a border around it. The [onAnswer] callback
 * will be called whenever the user answers a question, and it will be passed the updated survey.
 * The [showErrors] parameter will be passed to the [Survey.Render] function to indicate whether
 * errors should be shown for unanswered questions.
 */
@Composable
fun ColumnScope.SurveyView(
    survey: Survey,
    showErrors: Boolean = false,
    onAnswer: ((Survey) -> Unit)? = null,
) {
    survey.Render(
        Modifier
            .weight(1f)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.shapes.medium,
            ).padding(10.dp)
            .fillMaxWidth(),
        showErrors,
        onAnswer,
    )
}

/**
 * Loads the initial answers for the first two questions from the previous submission if available.
 */
suspend fun loadInitialAnswers(
    survey: Survey,
    repository: SurveyRepository,
    vm: SurveyVM,
) {
    val questions = survey.questions
    val q1 = questions[0] as QuestionWithMultiOptions
    val q2 = questions[1] as QuestionWithMultiOptionsAndOther
    if (q1.answer == null || q2.answer == null) {
        val latestResult = repository.latestResult.first()
        if (latestResult != null) {
            val surveyId = latestResult.id
            val q1result = repository.multiQuestionResult(surveyId, q1.id).first()
            val q2result = repository.multiQuestionResult(surveyId, q2.id).first()
            val q1Answer = q1result?.answers ?: emptySet()
            val q2Answer = (q2result?.answers ?: emptySet()) to (q2result?.other ?: "")
            vm.update(
                survey
                    .update(q1.copy(answer = q1Answer))
                    .update(q2.copy(answer = q2Answer)),
            )
        }
    }
}