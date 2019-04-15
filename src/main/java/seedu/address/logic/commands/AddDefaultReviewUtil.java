package seedu.address.logic.commands;

import java.util.List;

/**
 * Adds a restaurant to the food diary.
 */
public class AddDefaultReviewUtil {

    public static final String DEFAULT_ENTRY_1 = "Very poor, never ever go again.";
    public static final String DEFAULT_ENTRY_2 = "Below average, try not to go again.";
    public static final String DEFAULT_ENTRY_3 = "Average, normal.";
    public static final String DEFAULT_ENTRY_4 = "Good, would go again.";
    public static final String DEFAULT_ENTRY_5 = "Excellent,  must go again.";

    public static final List<String> DEFAULT_ENTRIES = List.of(DEFAULT_ENTRY_1, DEFAULT_ENTRY_2, DEFAULT_ENTRY_3,
            DEFAULT_ENTRY_4, DEFAULT_ENTRY_5);
}
