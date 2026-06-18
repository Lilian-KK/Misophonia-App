package edu.moravian.csci215.misophoniaapp.survey_data

import kotlinx.coroutines.flow.first
import kotlin.collections.copy
import kotlin.jvm.JvmName
import kotlin.text.iterator

/**
 * Saves the current survey result to the repository. This should be called when the user completes
 * the survey.
 */
@JvmName("saveSurvey")
suspend fun Survey.save(surveyType: SurveyType, repository: SurveyRepository) = this.questions.save(surveyType, repository)

/**
 * Saves the current survey result to the repository. This should be called when the user completes
 * the survey.
 */
@JvmName("saveSurveyQuestions")
suspend fun SurveyQuestions.save(surveyType: SurveyType, repository: SurveyRepository) {
    val surveyId = repository.saveResult(this.score, surveyType)
    for (question in this) {
        when (question) {
            is QuestionWithSingleOption -> {
                repository.saveSingleQuestion(
                    surveyId = surveyId,
                    questionId = question.id,
                    answer = question.answer ?: -1,
                )
            }

            is QuestionWithMultiOptions -> {
                repository.saveMultiQuestion(
                    surveyId = surveyId,
                    questionId = question.id,
                    answers = question.answer ?: emptySet(),
                )
            }

            is QuestionWithMultiOptionsAndOther -> {
                repository.saveMultiQuestion(
                    surveyId = surveyId,
                    questionId = question.id,
                    answers = question.answer?.first ?: emptySet(),
                    other = question.answer?.second,
                )
            }
        }
    }
}

/**
 * Loads the survey result with the given ID from the repository and maps it back to a Survey.
 */
suspend fun Survey.load(
    surveyId: Long,
    repository: SurveyRepository,
): Survey {
    // Load the answers for all questions
    val singleResults = repository.singleQuestionResults(surveyId).first()
    val multiResults = repository.multiQuestionResults(surveyId).first()
    // Map the results back to the questions
    return this.map { question ->
        when (question) {
            is QuestionWithSingleOption -> {
                val answer = singleResults.firstOrNull { it.questionId == question.id }?.answer
                if (answer == null) question else question.copy(answer = answer)
            }

            is QuestionWithMultiOptions -> {
                val answer = multiResults.firstOrNull { it.questionId == question.id }?.answers
                if (answer == null) question else question.copy(answer = answer)
            }

            is QuestionWithMultiOptionsAndOther -> {
                val answer = multiResults.firstOrNull { it.questionId == question.id }
                if (answer == null) question else question.copy(answer = answer.answers to (answer.other ?: ""))
            }

            else -> {
                question
            }
        }
    }
}
