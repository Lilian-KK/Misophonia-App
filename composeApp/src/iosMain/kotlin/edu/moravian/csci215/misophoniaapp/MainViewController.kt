package edu.moravian.csci215.misophoniaapp

import androidx.compose.ui.window.ComposeUIViewController
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository

fun MainViewController() = ComposeUIViewController {
    val repository = SurveyRepository(createSurveyDatabase().surveyResultDao())
    App(repository = repository)
}