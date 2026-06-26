package edu.moravian.csci215.misophoniaapp.survey_data

import edu.moravian.csci215.misophoniaapp.currentTimeMillis
import kotlinx.coroutines.flow.Flow

class SurveyRepository(
    private val dao: SurveyResultDao,
) {
    val latestResult: Flow<SurveyResultEntity?> = dao.observeLatest()
    val allResults: Flow<List<SurveyResultEntity>> = dao.observeAll()

    fun getResult(id: Long): Flow<SurveyResultEntity?> = dao.observeById(id)

    fun singleQuestionResults(surveyResultId: Long) = dao.answersForSingleQuestions(surveyResultId)

    fun multiQuestionResults(surveyResultId: Long) = dao.answersForMultiQuestions(surveyResultId)

    fun sliderQuestionResults(surveyResultId: Long) = dao.answersForSliderQuestions(surveyResultId)

    fun thisorthatQuestionResults(surveyResultId: Long) = dao.answersForThisOrThatQuestions(surveyResultId)


    fun singleQuestionResult(
        surveyResultId: Long,
        questionId: String,
    ) = dao.answerForSingleQuestion(surveyResultId, questionId)

    fun multiQuestionResult(
        surveyResultId: Long,
        questionId: String,
    ) = dao.answerForMultiQuestion(surveyResultId, questionId)

    suspend fun saveResult(score: Int, surveyType: SurveyType): Long =
        dao.insert(
            SurveyResultEntity(
                surveyType = surveyType,
                completedAtEpochMillis = currentTimeMillis(),
                totalScore = score,
            ),
        )

    suspend fun saveSingleQuestion(
        surveyId: Long,
        questionId: String,
        answer: Int,
    ): Long =
        dao.insert(
            SurveyQuestionSingleAnswerResultEntity(
                surveyResultId = surveyId,
                questionId = questionId,
                answer = answer,
            ),
        )

    suspend fun saveMultiQuestion(
        surveyId: Long,
        questionId: String,
        answers: Set<Int>,
        other: String? = null,
    ): Long =
        dao.insert(
            SurveyQuestionMultiAnswerResultEntity(
                surveyResultId = surveyId,
                questionId = questionId,
                answers = answers,
                other = other,
            ),
        )

    suspend fun saveSliderQuestion(
        surveyId: Long,
        questionId: String,
        answer: Int,
    ): Long =
        dao.insert(
            SurveyQuestionSliderAnswerResultEntity(
                surveyResultId = surveyId,
                questionId = questionId,
                answer = answer,
            ),
        )

    suspend fun saveThisOrThatQuestion(
        surveyId: Long,
        questionId: String,
        answer: Int,
    ): Long =
        dao.insert(
            SurveyQuestionThisOrThatResultEntity(
                surveyResultId = surveyId,
                questionId = questionId,
                answer = answer,
            ),
        )
}
