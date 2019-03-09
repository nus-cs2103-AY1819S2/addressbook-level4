package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class GradeRange {

    private final Grade expectedMinGrade;
    private final Grade expectedMaxGrade;

    public GradeRange(Grade expectedMinGrade, Grade expectedMaxGrade) {
        requireAllNonNull(expectedMinGrade, expectedMaxGrade);
        this.expectedMinGrade = expectedMinGrade;
        this.expectedMaxGrade = expectedMaxGrade;
    }

    public Grade getExpectedMinGrade() {
        return expectedMinGrade;
    }

    public Grade getExpectedMaxGrade() {
        return expectedMaxGrade;
    }

    public boolean isWithinRange(Grade grade) {
        return grade.compareTo(expectedMinGrade) >= 0
                && grade.compareTo(expectedMaxGrade) <= 0;
    }



}
