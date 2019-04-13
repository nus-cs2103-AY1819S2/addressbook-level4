package seedu.address.model.job;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Represents a JobListName in the address book.
 */
public enum JobListName {
    APPLICANT(), KIV(), INTERVIEW(), SHORTLIST(), STUB(), EMPTY();

    public static final String MESSAGE_CONSTRAINTS =
            "Job List Name should only be name of four job list or their prefixes.\n"
                    + "The valid job list name should be in following set:\n"
                    + "{Applicant, KIV, Interview, Shortlist, a, k, i, s}(case insensitive)";

    public static final String APPLICANT_NAME = "applicant";
    public static final String KIV_NAME = "kiv";
    public static final String INTERVIEW_NAME = "interview";
    public static final String SHORTLIST_NAME = "shortlist";
    public static final String APPLICANT_PREFIX = "a";
    public static final String KIV_PREFIX = "k";
    public static final String INTERVIEW_PREFIX = "i";
    public static final String SHORTLIST_PREFIX = "s";
    public static final String STUB_NAME = "stub";
    public static final String EMPTY_STRING = "";

    private static final String[] POSSIBLE_ListName = {APPLICANT_NAME, KIV_NAME, INTERVIEW_NAME, SHORTLIST_NAME,
        APPLICANT_PREFIX, KIV_PREFIX, INTERVIEW_PREFIX, SHORTLIST_PREFIX, STUB_NAME, EMPTY_STRING};
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
