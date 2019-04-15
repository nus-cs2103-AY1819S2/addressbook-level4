package seedu.address.testutil;

import seedu.address.model.project.ProjectName;

/**
 * A utility class containing a list of {@code ProjectName} objects to be used in tests.
 */
public class TypicalProjectNames {
    public static final ProjectName NON_EXISTENT_PROJECT_NAME = new ProjectName("DOES NOT EXIST");
    public static final ProjectName TYPICAL_PROJECT_NAME_1 = new ProjectName("PROJECT 1");
    public static final ProjectName TYPICAL_PROJECT_NAME_2 = new ProjectName("PROJECT 2");

    public static final ProjectName TYPICAL_PROJECT_NAME_INDEX_1 = new ProjectName("Project Alice hey");
    public static final ProjectName TYPICAL_PROJECT_NAME_INDEX_2 = new ProjectName("Project Benson");
}
