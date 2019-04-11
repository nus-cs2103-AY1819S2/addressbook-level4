package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Helper functions for handling doctors (for match-doctor command).
 */
public class DoctorUtil {
    /**
     * Specially for the match-doctor command.
     * Checks the appointment doctor ID and the specialisation-matched doctor ID.
     * @param apptDid
     * @param specMatchedDid
     * @return true if they are the same.
     */
    public static boolean containsDoctor(int apptDid, int specMatchedDid) {
        return apptDid == specMatchedDid;
    }

    /**
     * Specially for list-doctor KEYWORD command.
     * Checks if the keyword is a substring of the sentence, ignoring case.
     * @param sentenceToCheck
     * @param keyword
     * @return true if the sentence contains the keyword.
     */
    public static boolean containsKeyword(String sentenceToCheck, String keyword) {
        requireNonNull(sentenceToCheck);
        requireNonNull(keyword);

        String preppedWord = keyword.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");

        String preppedSentenceToCheck = sentenceToCheck.trim().toLowerCase();

        return preppedSentenceToCheck
                .contains(preppedWord); // return true as long as any part of the sentence contains keyword
    }
}
