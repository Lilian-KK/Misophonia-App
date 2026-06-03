package edu.moravian.csci215.misophoniaapp.surveys

import edu.moravian.csci215.misophoniaapp.survey_data.Instruction
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithSingleOption
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement

private val SCALE_OPTIONS = listOf("0", "1", "2", "3", "4")
private val YES_NO_OPTIONS = listOf("Yes", "No")

private val DEFAULT_SCORING = mapOf(0 to 0, 1 to 1, 2 to 2, 3 to 3, 4 to 4)

val DUKE_SURVEY: List<SurveyElement> =
    listOf(
        Instruction(
            "main_instructions",
            "The following questions refer to the experience of being intensely bothered by a sound or sounds, even when they" +
                "are not overly loud. These can be human or non‐human sounds, or the sight of someone or something making a" +
                "sound that you can't hear (e.g., the sight of someone biting their nails from across the room).",
        ),
        Instruction(
            "yes_no_instructions",
            "Please indicate whether the following sounds and/or sights bother you much more intensely than they do most " +
                "other people",
        ),
        QuestionWithSingleOption(
            "bother1",
            "1. People making mouth sounds while eating or drinking (e.g., chewing, crunching, " +
                "slurping).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother2",
            "2. People making nasal/throat sounds (e.g., sniffing, sneezing, nose‐whistling, coughing, " +
                "throat‐clearing).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother3",
            "3. People making mouth sounds when not eating (e.g., making the \"tsk\" sound, heavy " +
                "breathing, snoring, whistling).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother4",
            "4. People making repetitive sounds (e.g., typing, tapping nails on table, pen clicking, " +
                "writing, construction work, using machinery).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother5",
            "5. Rustling or tearing objects (e.g., paper, plastic).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother6",
            "6. Speech sounds (e.g., \"p\" sounds, hissing \"s\" sounds, someone speaking with a lisp, " +
                "high‐pitched voices).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother7",
            "7. Body or joint sounds (e.g., snapping fingers, cracking joints, jaw clicking).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother8",
            "8. Rubbing sounds (e.g., hands on pants, hands against one another, Styrofoam rubbing " +
                "together).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother9",
            "9. Stomping or loud walking (e.g., heels clicking, flip flops, etc.).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother10",
            "10. Muffled sounds (e.g., voices separated by a wall, TV /music in another room).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother11",
            "11. People talking in the background (e.g., phone calls in public, many people talking at " +
                "once).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother12",
            "12. Repetitive or continuous sounds not made by a person (e.g., clock ticking, air " +
                "conditioner humming, water running).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother13",
            "13. Animals making repetitive sounds (e.g., licking, chirping, barking, eating, drinking).",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother14",
            "14. Seeing someone making or about to make a sound that bothers you, even if you can't " +
                "hear it (e.g., seeing someone reach into a bag of chips, seeing someone eating on TV " +
                "with the volume off).",
            YES_NO_OPTIONS,
        ),
        // fix this one
        QuestionWithSingleOption(
            "bother15",
            "15. NOTE: FIX THIS ONE. it has an other box AND yes/no. Other (please describe):",
            YES_NO_OPTIONS,
        ),
        QuestionWithSingleOption(
            "bother16",
            "16. There are no specific sounds that bother me much more than they do other people",
            YES_NO_OPTIONS,
        ),
        Instruction(
            "sixteenyes",
            "Note: If items 1-15 are marked \"no\" and item 16 is marked \"yes\", do not move forward with the following sections.",
        ),
    )
