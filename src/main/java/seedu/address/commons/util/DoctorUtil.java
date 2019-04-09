package seedu.address.commons.util;

/**
 * Helper functions for handling doctors (for match-doctor command).
 */
public class DoctorUtil {

    public static boolean containsDoctor(int apptDid, int specMatchedDid) {
        return apptDid == specMatchedDid;
    }
}
