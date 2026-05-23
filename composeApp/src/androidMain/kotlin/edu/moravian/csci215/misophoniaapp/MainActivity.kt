package edu.moravian.csci215.misophoniaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val repository = SurveyRepository(createSurveyDatabase(applicationContext).surveyResultDao())


        setContent {
            App(repository = repository)
        }
    }
}