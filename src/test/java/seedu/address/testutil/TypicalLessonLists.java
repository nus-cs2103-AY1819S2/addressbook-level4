package seedu.address.testutil;

import seedu.address.model.lesson.LessonList;

/**
 * A utility class containing a list of {@code Card} objects used for {@code Card} testing.
 */
public class TypicalLessonLists {
    public static final LessonList LESSONLIST_DEFAULT =
            new LessonListBuilder().withLessons(TypicalLessons.getTypicalLessons()).build();
}
