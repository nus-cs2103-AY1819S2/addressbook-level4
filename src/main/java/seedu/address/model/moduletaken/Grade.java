package seedu.address.model.moduletaken;

/**
 * Represents grade of a module taken
 */
public enum Grade {

    A_PLUS(5, true, true, "A+"),
    A(5, true, true, "A"),
    A_MINUS(4.5, true, true, "A-"),
    B_PLUS(4, true, true, "B+"),
    B(3.5, true, true, "B"),
    B_MINUS(3, true, true, "B-"),
    C_PLUS(2.5, true, true, "C+"),
    C(2, true, true, "C"),
    D_PLUS(1.5, true, true, "D+"),
    D(1, true, true, "D"),
    F(0, false, true, "F"),
    CS(0, true, false, "CS"),
    CU(0, false, false, "CU"),
    EXE(0, true, false, "EXE"),
    IC(0, false, false, "IC"),
    S(0, true, false, "S"),
    U(0, false, false, "U"),
    W(0, false, false, "W");

    public static final String MESSAGE_CONSTRAINTS = "Grade must be from A+ to F. or S/U";
    private final double gradePoint;
    private final boolean isPass;
    private final boolean isCounted;
    private final String printedGrade;

    Grade(double gradePoint, boolean isPass, boolean isCounted, String printedGrade) {
        this.gradePoint = gradePoint;
        this.isPass = isPass;
        this.isCounted = isCounted;
        this.printedGrade = printedGrade;
    }

    double getGradePoint() {
        return this.gradePoint;
    }

    boolean isPassingGrade() {
        return this.isPass;
    }

    boolean isCountedInCap() {
        return this.isCounted;
    }

    public static Grade getGrade(String grade) {
        switch (grade) {
        case "A_PLUS":
            return A_PLUS;
        case "A":
            return A;
        case "A_MINUS":
            return A_MINUS;
        case "B_PLUS":
            return B_PLUS;
        case "B":
            return B;
        case "B_MINUS":
            return B_MINUS;
        case "C_PLUS":
            return C_PLUS;
        case "C":
            return C;
        case "D_PLUS":
            return D_PLUS;
        case "D":
            return D;
        case "F":
            return F;
        case "CS":
            return CS;
        case "CU":
            return CU;
        case "W":
            return W;
        case "IC":
            return IC;
        case "EXE":
        default:
            return EXE;
        }
    }

    /**
     * Returns if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        switch (test) {
        case "A_PLUS":
        case "A":
        case "A_MINUS":
        case "B_PLUS":
        case "B":
        case "B_MINUS":
        case "C_PLUS":
        case "C":
        case "D_PLUS":
        case "D":
        case "F":
        case "CS":
        case "CU":
        case "W":
        case "IC":
        case "EXE":
            return true;
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        return this.printedGrade;
    }

    /**
     * Returns if this gradepoint is no more than another gradepoint.
     */
    public boolean isLowerOrEqualTo(Grade limit) {
        return this.gradePoint <= limit.gradePoint;
    }

    /**
     * Checks if this grade is within the given grade range.
     * @param range The grade range to be checked against.
     * @return true if this grade is within this grade range, false otherwise.
     */
    public boolean isWithin(GradeRange range) {
        return isLowerOrEqualTo(range.getMax()) && range.getMin().isLowerOrEqualTo(this);
    }
}
