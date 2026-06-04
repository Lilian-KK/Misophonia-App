package edu.moravian.csci215.misophoniaapp.survey_data

import androidx.lifecycle.ViewModel
import edu.moravian.csci215.misophoniaapp.surveys.AMISOS_R_SURVEY
import edu.moravian.csci215.misophoniaapp.surveys.DUKE_SURVEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import misophoniaapp.composeapp.generated.resources.Res
import misophoniaapp.composeapp.generated.resources.answer_all
import org.jetbrains.compose.resources.getString

/**
 * ViewModel for the survey screen. Holds the current state of the survey and handles saving.
 */
class SurveyVM(
    val repository: SurveyRepository,
) : ViewModel() {
    private val _errorText = MutableStateFlow<String?>(null)
    val errorText: StateFlow<String?> = _errorText
    private val _survey = MutableStateFlow(DUKE_SURVEY)
    val survey: StateFlow<Survey> = _survey

    private val _saving = MutableStateFlow(false)
    val saving: StateFlow<Boolean> = _saving

    fun update(updated: Survey) {
        _survey.value = updated
    }

    suspend fun save(onCompleted: () -> Unit) {
        _saving.value = true
        if (survey.value.questions.hasErrors) {
            _errorText.value = getString(Res.string.answer_all)
        } else {
            survey.value.save(repository)
            onCompleted()
        }
        _saving.value = false
    }
}
