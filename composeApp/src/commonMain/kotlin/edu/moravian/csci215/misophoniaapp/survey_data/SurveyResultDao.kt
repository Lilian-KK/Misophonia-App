package edu.moravian.csci215.misophoniaapp.survey_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SurveyResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SurveyQuestionSingleAnswerResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SurveyQuestionMultiAnswerResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SurveyQuestionSliderAnswerResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SurveyQuestionThisOrThatResultEntity): Long

    @Query("SELECT * FROM survey_results ORDER BY completedAtEpochMillis DESC LIMIT 1")
    fun observeLatest(): Flow<SurveyResultEntity?>

    @Query("SELECT * FROM survey_results ORDER BY completedAtEpochMillis DESC")
    fun observeAll(): Flow<List<SurveyResultEntity>>

    @Query("SELECT * FROM survey_results WHERE id = :id")
    fun observeById(id: Long): Flow<SurveyResultEntity?>

    @Query("SELECT * FROM survey_question_single_answer_results WHERE surveyResultId = :surveyResultId AND questionId = :questionId")
    fun answerForSingleQuestion(
        surveyResultId: Long,
        questionId: String,
    ): Flow<SurveyQuestionSingleAnswerResultEntity?>

    @Query("SELECT * FROM survey_question_multi_answer_results WHERE surveyResultId = :surveyResultId AND questionId = :questionId")
    fun answerForMultiQuestion(
        surveyResultId: Long,
        questionId: String,
    ): Flow<SurveyQuestionMultiAnswerResultEntity?>

    @Query("SELECT * FROM survey_question_slider_answer_results WHERE surveyResultId = :surveyResultId AND questionId = :questionId")
    fun answerForSliderQuestion(
        surveyResultId: Long,
        questionId: String,
    ): Flow<SurveyQuestionSliderAnswerResultEntity?>

    @Query("SELECT * FROM survey_question_thisorthat_answer_results WHERE surveyResultId = :surveyResultId AND questionId = :questionId")
    fun answerForThisOrThatQuestion(
        surveyResultId: Long,
        questionId: String,
    ): Flow<SurveyQuestionThisOrThatResultEntity?>

    @Query("SELECT * FROM survey_question_single_answer_results WHERE surveyResultId = :surveyResultId")
    fun answersForSingleQuestions(surveyResultId: Long): Flow<List<SurveyQuestionSingleAnswerResultEntity>>

    @Query("SELECT * FROM survey_question_multi_answer_results WHERE surveyResultId = :surveyResultId")
    fun answersForMultiQuestions(surveyResultId: Long): Flow<List<SurveyQuestionMultiAnswerResultEntity>>

    @Query("SELECT * FROM survey_question_slider_answer_results WHERE surveyResultId = :surveyResultId")
    fun answersForSliderQuestions(surveyResultId: Long): Flow<List<SurveyQuestionSliderAnswerResultEntity>>

    @Query("SELECT * FROM survey_question_thisorthat_answer_results WHERE surveyResultId = :surveyResultId")
    fun answersForThisOrThatQuestions(surveyResultId: Long): Flow<List<SurveyQuestionThisOrThatResultEntity>>

}
