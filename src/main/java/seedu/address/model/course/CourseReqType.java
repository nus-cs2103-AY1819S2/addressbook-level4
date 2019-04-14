package seedu.address.model.course;

/**
 * Represents type of course requirement
 */
public enum CourseReqType {

    /* in decreasing order of priority */
    CORE("Core Requirement"),
    BD("Breadth & Depth"),
    IE("Industry Experience Requirement"),
    FAC("Faculty Requirement"),
    GE("General Education Requirement"),
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
