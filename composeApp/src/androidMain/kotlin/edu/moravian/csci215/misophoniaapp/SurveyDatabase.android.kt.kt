package edu.moravian.csci215.misophoniaapp

import android.content.Context
import androidx.room.Room
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyDatabase
import edu.moravian.csci215.misophoniaapp.survey_data.buildSurveyDatabase

fun createSurveyDatabase(context: Context): SurveyDatabase {
    val appContext = context.applicationContext
    val dbPath = appContext.getDatabasePath("survey_results.db").absolutePath
    val builder =
        Room.databaseBuilder<SurveyDatabase>(
            context = appContext,
            name = dbPath,
        )
    return buildSurveyDatabase(builder)
}
