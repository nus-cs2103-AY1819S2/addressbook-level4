package seedu.address.model.job;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Represents a JobListName in the address book.
 */
public enum JobListName {
    APPLICANT(), KIV(), INTERVIEW(), SHORTLIST();

    public static final String MESSAGE_CONSTRAINTS =
        "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String APPLICANT_NAME = "applicant";
    public static final String KIV_NAME = "kiv";
    public static final String INTERVIEW_NAME = "interview";
    public static final String SHORTLIST_NAME = "shortlist";
    public static final String APPLICANT_PREFIX = "a";
    public static final String KIV_PREFIX = "k";
    public static final String INTERVIEW_PREFIX = "i";
    public static final String SHORTLIST_PREFIX = "s";

    private static final String[] POSSIBLE_ListName = {APPLICANT_NAME, KIV_NAME, INTERVIEW_NAME, SHORTLIST_NAME,
        APPLICANT_PREFIX, KIV_PREFIX, INTERVIEW_PREFIX, SHORTLIST_PREFIX};
    private static final TreeSet<String> POSSIBLE_ListName_TREE = new TreeSet<>(Arrays.asList(POSSIBLE_ListName));

    /**
     * Returns true if a given string is a valid list name.
     */
    public static boolean isValidJobListName(String test) {
        if (test == null) {
            throw new NullPointerException("Parameter Type cannot be null");
        }
        return POSSIBLE_ListName_TREE.contains(test);
    }

}
