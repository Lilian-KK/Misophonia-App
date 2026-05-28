package edu.moravian.csci215.misophoniaapp

import androidx.room.Room
import edu.moravian.csci215.misophoniaapp.survey_data.buildSurveyDatabase
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun createSurveyDatabase(): SurveyDatabase {
    val documentsUrl =
        checkNotNull(
            NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            ),
        )

    val dbPath = checkNotNull(documentsUrl.path) + "/survey_results.db"
    val builder = Room.databaseBuilder<SurveyDatabase>(name = dbPath)
    return buildSurveyDatabase(builder)
}
