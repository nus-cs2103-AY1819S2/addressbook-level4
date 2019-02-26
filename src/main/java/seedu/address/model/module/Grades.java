package seedu.address.model.module;

/**
 * Represents grades of a module taken
 */
public enum Grades {

    APlus (5, true, true, "A+"),
    A (5, true, true, "A"),
    AMinus (4.5, true, true, "A-"),
    BPlus (4, true, true, "B+"),
    B (3.5, true, true, "B"),
    BMinus (3, true, true, "B-"),
    CPlus (2.5, true, true, "C+"),
    C (2, true, true, "C"),
    DPlus (1.5, true, true, "D+"),
    D (1, true, true, "D"),
    F (0, false, true, "F"),
    CS (0, true, false, "CS"),
    CU (0, false, false, "CU"),
    EXE (0, true, false, "EXE"),
    IC (0, false, false, "IC"),
    S (0, true, false, "S"),
    U (0, false, false, "U"),
    W (0, false, false, "W");

    private final double gradePoint;
    private final boolean isPass;
    private final boolean isCounted;
    private final String grade;

    Grades (double gradePoint, boolean isPass, boolean isCounted, String grade) {
        this.gradePoint = gradePoint;
        this.isPass = isPass;
        this.isCounted = isCounted;
        this.grade = grade;
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

    Grades getGrades(String grades) {
        switch(grades) {
        case "A+":
            return APlus;
        case "A":
            return A;
        case "A-":
            return AMinus;
        case "B+":
            return BPlus;
        case "B":
            return B;
        case "B-":
            return BMinus;
        case "C+":
            return CPlus;
        case "C":
            return C;
        case "D+":
            return DPlus;
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
        case "EXE": default:
            return EXE;
        }
    }
    @Override
    public String toString() {
        return this.grade;
    }
}
