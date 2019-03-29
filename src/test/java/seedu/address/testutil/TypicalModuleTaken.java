package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURE_HOUR_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURE_HOUR_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.GradTrak;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * A utility class containing a list of {@code ModuleTaken} objects to be used in tests.
 */
public class TypicalModuleTaken {

    public static final ModuleTaken CS2103T = new ModuleTakenBuilder().withModuleInfoCode("CS2103T")
            .withExpectedMinGrade("F").withExpectedMaxGrade("A").withLectureHour("0")
            .withSemester("Y1S2")
            .withTags("friends").build();
    public static final ModuleTaken CS2101 = new ModuleTakenBuilder().withModuleInfoCode("CS2101")
            .withExpectedMinGrade("D")
            .withExpectedMaxGrade("A").withLectureHour("0").withSemester("Y3S2")
            .withTags("owesMoney", "friends").build();
    public static final ModuleTaken CS1010S = new ModuleTakenBuilder()
            .withModuleInfoCode("CS1010S")
            .withSemester("Y5S2")
            .withExpectedMinGrade("D")
            .withExpectedMaxGrade("A")
            .withLectureHour("0").build();
    public static final ModuleTaken CS1010X = new ModuleTakenBuilder()
            .withModuleInfoCode("CS1010X")
            .withSemester("Y4S2")
            .withExpectedMinGrade("F")
            .withExpectedMaxGrade("A")
            .withLectureHour("0")
            .withTags("friends").build();
    public static final ModuleTaken MA1521 = new ModuleTakenBuilder()
            .withModuleInfoCode("MA1521")
            .withSemester("Y3S1")
            .withExpectedMinGrade("C")
            .withExpectedMaxGrade("A")
            .withLectureHour("0").build();
    public static final ModuleTaken LSM1301 = new ModuleTakenBuilder()
            .withModuleInfoCode("LSM1301")
            .withSemester("Y4S2")
            .withExpectedMinGrade("D")
            .withExpectedMaxGrade("A")
            .withLectureHour("0").build();
    public static final ModuleTaken GER1000 = new ModuleTakenBuilder()
            .withModuleInfoCode("GER1000")
            .withSemester("Y4S1")
            .withExpectedMinGrade("C")
            .withExpectedMaxGrade("B")
            .withLectureHour("0").build();

    // Manually added
    public static final ModuleTaken CS2030 = new ModuleTakenBuilder()
            .withModuleInfoCode("CS2030")
            .withSemester("Y1S1")
            .withExpectedMinGrade("B")
            .withExpectedMaxGrade("A")
            .withLectureHour("0").build();
    public static final ModuleTaken CS2040 = new ModuleTakenBuilder()
            .withModuleInfoCode("CS2040")
            .withSemester("Y3S2")
            .withExpectedMinGrade("D")
            .withExpectedMaxGrade("A")
            .withLectureHour("0").build();

    // Manually added - ModuleTaken's details found in {@code CommandTestUtil}
    public static final ModuleTaken DEFAULT_MODULE_CS2103T = new ModuleTakenBuilder()
            .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
            .withSemester(VALID_SEMESTER_CS2103T)
            .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS2103T)
            .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS2103T)
            .withLectureHour(VALID_LECTURE_HOUR_CS2103T)
            .withTags(VALID_TAG_FRIEND).build();
    public static final ModuleTaken DEFAULT_MODULE_CS1010 = new ModuleTakenBuilder()
            .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
            .withSemester(VALID_SEMESTER_CS1010)
            .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS1010)
            .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010)
            .withLectureHour(VALID_LECTURE_HOUR_CS1010)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_CS2103T = "CS2103T";
    public static final String KEYWORD_MATCHING_MA1521 = "MA1521";
    public static final String KEYWORD_MATCHING_CS = "cs";
    public static final String KEYWORD_MATCHING_MA = "ma";
    public static final String KEYWORD_MATCHING_GE = "ge";

    private TypicalModuleTaken() {} // prevents instantiation

    /**
     * Returns an {@code GradTrak} with all the typical persons.
     */
    public static GradTrak getTypicalGradTrak() {
        GradTrak gt = new GradTrak();
        for (ModuleTaken moduleTaken : getTypicalModulesTaken()) {
            gt.addModuleTaken(moduleTaken);
        }

        return gt;
    }

    public static List<ModuleTaken> getTypicalModulesTaken() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2101, CS1010S, CS1010X, MA1521, LSM1301, GER1000));
    }
}
