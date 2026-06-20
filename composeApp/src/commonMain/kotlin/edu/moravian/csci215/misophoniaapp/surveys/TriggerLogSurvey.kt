package edu.moravian.csci215.misophoniaapp.surveys

import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithMultiOptions
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithSingleOption
import edu.moravian.csci215.misophoniaapp.survey_data.SliderQuestion
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement

val TRIGGER_LOG_SURVEY: List<SurveyElement> =
    listOf(
        //put the tag ui here
        SliderQuestion(
            "ratingResponseSeverity",
            "Rate the severity of your misophonia response.",
            mapOf(
                0 to "Level 0: Person with misophonia hears a known trigger sound but feels no discomfort.",
                1 to "Level 1: Person with misophonia is aware of the presence of a known trigger person but feels no, or minimal, anticipatory anxiety.",
                2 to "Level 2: Known trigger sound elicits minimal psychic discomfort, irritation or annoyance. No symptoms of panic or fight or flight response.",
                3 to "Level 3: Person with misophonia feels increasing levels of psychic discomfort but does not engage in any physical response. Sufferer may be hyper-vigilant to audio-visual stimuli.",
                4 to "Level 4: Person with misophonia engages in a minimal physical response - non-confrontational coping behaviours, such as asking the trigger person to stop making the noise, discreetly covering one ear, or by calmly moving away from the noise. No panic or flight or flight symptoms exhibited.",
                5 to "Level 5: Person with misophonia adopts more confrontational coping mechanisms, such as overtly covering their ears, mimicking the trigger person, engaging in other echolalias, or displaying overt irritation.",
                6 to "Level 6: Person with misophonia experiences substantial psychic discomfort. Symptoms of panic, and a fight or flight response, begin to engage.",
                7 to "Level 7: Person with misophonia experiences substantial psychic discomfort. Increasing use (louder, more frequent) use of confrontational coping mechanisms. There may be unwanted sexual arousal. Sufferer may re-imagine the trigger sound and visual cues over and over again, sometimes for weeks, months or even years after the event.",
                8 to "Level 8: Person with misophonia experiences substantial psychic discomfort. Some violence ideation.",
                9 to "Level 9: Panic/rage reaction in full swing. Conscious decision not to use violence on trigger person. Actual flight from vicinity of noise and/or use of physical violence on an inanimate object. Panic, anger or severe irritation may be manifest in sufferer's demeanour.",
                10 to "Level 10: Actual use of physical violence on a person or animal (i.e., a household pet). Violence may be inflicted on self (self-harming)."
            )
        ),
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
