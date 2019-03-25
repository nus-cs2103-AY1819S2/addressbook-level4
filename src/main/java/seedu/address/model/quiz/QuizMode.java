package seedu.address.model.quiz;

/**
 * Different types of mode supported in Quiz.
 * Learn: sees both the question and answer first then get tested.
 * Review: only get tested.
 * Preview: sees both question and answer but not tested.
 */
public enum QuizMode {
    LEARN,
    REVIEW,
    PREVIEW
}
