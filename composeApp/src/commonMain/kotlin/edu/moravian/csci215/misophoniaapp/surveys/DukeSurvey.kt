package edu.moravian.csci215.misophoniaapp.surveys

import edu.moravian.csci215.misophoniaapp.survey_data.Instruction
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithMultiOptions
import edu.moravian.csci215.misophoniaapp.survey_data.QuestionWithSingleOption
import edu.moravian.csci215.misophoniaapp.survey_data.SurveyElement

private val SCALE_OPTIONS = listOf("0", "1", "2", "3", "4")
private val YES_NO_OPTIONS = listOf("Yes", "No")

private val DEFAULT_SCORING = mapOf(0 to 0, 1 to 1, 2 to 2, 3 to 3, 4 to 4)

val DUKE_SURVEY: List<SurveyElement> =
    listOf(
        Instruction(
            "main_instructions",
            "The following questions refer to the experience of being intensely bothered by a sound or sounds, even when they " +
                "are not overly loud. These can be human or non‐human sounds, or the sight of someone or something making a " +
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
        //this one is strange on the pdf, is it a real question?
        QuestionWithMultiOptions(
            "howOftenBothered",
            "In the past month, on average across ALL bothersome sounds, rate how often you were bothered by a sound/sounds.",
            listOf(
                "Once per month or less",
                "2-3 times per month",
                "1-3 times per week",
                "4-7 times per week",
                "2-5 times per day",
                "6 or more times per day"
            )
        ),
        Instruction(
            "scaleInstruct",
            "For the following sections, please use the scale below: \n" +
                    "0 -> Never\n1 -> Rarely\n2 -> Sometimes\n3 -> Often\n4 -> Always/almost always"
        ),
        Instruction(
            "howOftenFeeling",
            "In the past month on average, when intensely bothered by a sound or sounds, please rate how often you felt each of the following:"
        ),
        //these have a score attribute attached to them
        QuestionWithMultiOptions(
            "felt1",
            "I felt angry.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt2",
            "I felt anxious.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt3",
            "I felt disgusted.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt4",
            "I felt hateful.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt5",
            "I felt panic.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt6",
            "I felt hostile.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt7",
            "I felt jittery.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "felt8",
            "I felt frustrated.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howOftenTheFollowingHappened",
            "In the past month on average, when intensely bothered by a sound or sounds, please rate how often each of the following happened to you."
        ),
        QuestionWithMultiOptions(
            "happened1",
            "I became rigid or stiff.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "happened2",
            "I trembled or shuddered.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "happened3",
            "My heart pounded or raced.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "happened4",
            "I started breathing intensely or forcefully.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "happened5",
            "I reflexively jumped.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howOftenThoughtsHad",
            "In the past month on average, when intensely bothered by a sound or sounds, please rate how often you had each of the following thoughts."
        ),
        QuestionWithMultiOptions(
            "thought1",
            "“I am helpless.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought2",
            "“I want to cry.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought3",
            "“How do I make this sound stop?”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought4",
            "“Everything is awful.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought5",
            "“I cannot handle this.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought6",
            "“I need to get away from the sound.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought7",
            "“I would do anything to make it stop.”",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought8",
            "I thought about screaming at, yelling at, or telling off the person making the sound.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought9",
            "I thought about pushing, poking, shoving, etc. the person making the sound.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "thought10",
            "I thought about physically hurting the person making the sound.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howOftenBeforeHearingSound",
            "Please rate how often you did the following in the past month, on average, BEFORE HEARING a bothersome sound."
        ),
        QuestionWithMultiOptions(
            "beforehearing1",
             "I avoided certain people, places, or things so I would not have to hear sounds I dislike.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "beforehearing2",
            "I used a different sound to drown the bothersome sound (e.g. turned on TV).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "beforehearing3",
            "I used strategies to make myself less bothered by sounds I might hear (e.g. deep breathing, meditation, visualization).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "beforehearing4",
            "I was on guard for bothersome sounds",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "beforehearing5",
            "I distracted myself so as not to be bothered by a sound I might hear.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "beforehearing6",
            "I made a plan to cope with bothersome sounds if they occurred.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howOftenWhileHearingSound",
            "Please rate how often you did the following in the past month, on average, WHILE HEARING a bothersome sound."
        ),
        QuestionWithMultiOptions(
            "whilehearing1",
            "I blocked the sound (e.g., covered ears with hands, headphones, ear plugs)",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing2",
            "I used strategies to calm myself (e.g., self‐talk, breathing exercises).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing3",
            "I focused my attention on an activity (e.g., watched TV or videos).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing4",
            "I produced an alternate sound (e.g., humming).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing5",
            "I reminded myself that it could be worse.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing6",
            "I increased the background noise to cover up the bothersome sound (e.g. turned on TV, rolled down car window)",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing7",
            "I changed my way of thinking about the sound.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing8",
            "I looked away from the source of the sound.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing9",
            "I listened to music or a different sound.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "whilehearing10",
            "I mindfully focused on current sensations without judgement.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howOftenAterHearingSound",
            "Please rate how often you did the following in the past month, on average, AFTER HEARING a bothersome sound."
        ),
        QuestionWithMultiOptions(
            "afterhearing1",
            "I did something to comfort myself (e.g. exercised, went somewhere calming, pet animals).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "afterhearing2",
            "I listened to a comforting sound (e.g. white noise, music).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "afterhearing3",
            "I did some relaxation exercises (e.g. deep breathing, meditation).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "afterhearing4",
            "I used the sight, smell, or touch of an object to soothe myself (e.g. looked at a soothing picture, smelled a scent, or touched a soft blanket).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "afterhearing5",
            "I thought about strategies to help me cope better next time.",
            SCALE_OPTIONS,
        ),
        Instruction(
            "howMuchNegativeEffect",
            "Please rate the extent to which the bothersome sound/sounds and your reactions to them negatively affected the following in the past month, on average."
        ),
        QuestionWithMultiOptions(
            "negativeeffect1",
            "My ability to be with other people.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect2",
            "My performance at work or school.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect3",
            "The quality of my romantic relationships.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect4",
            "My ability to function in daily activities without help.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect5",
            "How much I enjoy spending time with my family.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect6",
            "My ability to work with others.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect7",
            "My self‐esteem.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect8",
            "My ability to maintain employment.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect9",
            "The quality of my relationships with my friends",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect10",
            "How connected I feel to other people.",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect11",
            "My ability to live with other people (e.g. roommate, partner).",
            SCALE_OPTIONS,
        ),
        QuestionWithMultiOptions(
            "negativeeffect12",
            "My ability to “be myself”.",
            SCALE_OPTIONS,
        ),
    )
