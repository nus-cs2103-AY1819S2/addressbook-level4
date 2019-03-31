package seedu.address.model.course;

/**
 * Represents type of course requirement
 */
public enum CourseReqType {

    /* in decreasing order of priority */
    CORE("Core"),
    TE("Technical Elective / Programme Elective / Focus Area"),
    GE("General Education"),
    UE("Unrestricted Elective");

    private final String printedType;

    CourseReqType(String printedType) {
        this.printedType = printedType;
    }

    @Override
    public String toString() {
        return printedType;
    }
}
