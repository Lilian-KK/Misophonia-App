package edu.moravian.csci215.misophoniaapp.survey_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_results")
data class SurveyResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val completedAtEpochMillis: Long,
    val totalScore: Int,
)

@Entity(tableName = "survey_question_single_answer_results")
data class SurveyQuestionSingleAnswerResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val surveyResultId: Long,
    val questionId: String,
    val answer: Int,
)

@Entity(tableName = "survey_question_multi_answer_results")
data class SurveyQuestionMultiAnswerResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val surveyResultId: Long,
    val questionId: String,
    val answers: Set<Int>,
    val other: String? = null,
)
