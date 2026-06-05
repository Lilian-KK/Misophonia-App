package edu.moravian.csci215.misophoniaapp.surveys

import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithMultiOptions
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithSingleOption
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement

val TRIGGER_LOG_SURVEY: List<SurveyElement> =
    listOf(
        //put the tag ui here
        QuestionWithSingleOption(
            "howLongLast",
            "How long did the \"trigger state\" last? This includes the initial reaction and any lingering negative emotions and physiological symptoms. (select one)",
            listOf(
                "immediately gone once the trigger is gone",
                "less than 1 hour",
                "multiple hours",
                "continues into the next day or longer"
            )
        ),
        QuestionWithMultiOptions(
            "relationshipToTriggerSource",
            "Relationship to trigger source (if applicable): (select many)",
            listOf(
                "family member",
                "friend",
                "coworker/acquaintance",
                "stranger",
                "other"
            )
        )
    )
