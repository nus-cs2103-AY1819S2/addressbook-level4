package seedu.address.testutil;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_EMPLOYEE = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_EMPLOYEE = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_EMPLOYEE = Index.fromOneBased(3);

    public static final Index INDEX_FIRST_PROJECT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PROJECT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PROJECT = Index.fromOneBased(3);

    public static final Index INDEX_FIRST_PROJECT_MILESTONE = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PROJECT_MILESTONE = Index.fromOneBased(2);
    public static final Index INDEX_INVALID_PROJECT_MILESTONE = Index.fromOneBased(3);

    public static final Index INDEX_FIRST_PROJECT_USER_STORY = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PROJECT_USER_STORY = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PROJECT_USER_STORY = Index.fromOneBased(3);
    public static final Index INDEX_INVALID_PROJECT_USER_STORY = Index.fromOneBased(3);

    public static final Index INDEX_FIRST_PROJECT_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PROJECT_TASK = Index.fromOneBased(2);
}
