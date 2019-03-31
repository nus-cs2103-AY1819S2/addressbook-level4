package seedu.address.model.course;

/**
 * Represents type of course requirement
 */
public enum CourseReqType {
    /* in decreasing order of priority */
    CORE, TE, GE, UE;

    /**
     * Returns full name of course requirement type
     */
    public String toPrintedString() {
        switch (this) {
        case CORE:
            return "Core requirement";
        case TE:
            return "Technical Elective/Focus Area";
        case GE:
            return "General Education";
        case UE:
        default:
            return "Unrestricted Elective";
        }
    }
}
