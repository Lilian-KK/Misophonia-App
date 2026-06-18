package edu.moravian.csci215.misophoniaapp.survey_data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        SurveyResultEntity::class,
        SurveyQuestionSingleAnswerResultEntity::class,
        SurveyQuestionMultiAnswerResultEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@ConstructedBy(SurveyDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun surveyResultDao(): SurveyResultDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SurveyDatabaseConstructor : RoomDatabaseConstructor<SurveyDatabase> {
    override fun initialize(): SurveyDatabase
}

fun buildSurveyDatabase(builder: RoomDatabase.Builder<SurveyDatabase>): SurveyDatabase =
    builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
