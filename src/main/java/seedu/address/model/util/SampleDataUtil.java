package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.GradTrak;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.limits.SemesterLimit;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code GradTrak} with sample data.
 */
public class SampleDataUtil {
    public static final int NUM_SEMS = 10;


    public static Semester getSampleCurrentSemester() {
        return Semester.Y1S2;
    }

    public static ModuleTaken[] getSampleModulesTaken() {
        return new ModuleTaken[] {
            new ModuleTaken(new ModuleInfoCode("CS1010"), Semester.valueOf("Y1S1"), Grade.valueOf("B"),
                Grade.valueOf("B"),
                    getTagSet("friends")),
            new ModuleTaken(new ModuleInfoCode("CS2040"), Semester.valueOf("Y1S1"), Grade.valueOf("B_PLUS"),
                Grade.valueOf("B_PLUS"),
                    getTagSet("programming", "friends")),
            new ModuleTaken(new ModuleInfoCode("CS2030"), Semester.valueOf("Y1S2"), Grade.valueOf("B"),
                    Grade.valueOf("B"),
                    getTagSet("programming", "friends")),
            new ModuleTaken(new ModuleInfoCode("MA1521"), Semester.valueOf("Y3S1"), Grade.valueOf("C"),
                    Grade.valueOf("A"),
                    getTagSet("math")),
            new ModuleTaken(new ModuleInfoCode("CS2101"), Semester.valueOf("Y3S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("communication")),
            new ModuleTaken(new ModuleInfoCode("CS2103T"), Semester.valueOf("Y3S1"), Grade.valueOf("C"),
                    Grade.valueOf("A"),
                    getTagSet("programming")),
            new ModuleTaken(new ModuleInfoCode("GER1000"), Semester.valueOf("Y3S2"), Grade.valueOf("C"),
                    Grade.valueOf("A"),
                    getTagSet("GEM")),
            new ModuleTaken(new ModuleInfoCode("LSM1301"), Semester.valueOf("Y4S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("friends"))
        };
    }

    public static List<SemesterLimit> getSampleSemesterLimits() {
        List<SemesterLimit> semList = new ArrayList<>();
        for (int i = 0; i < NUM_SEMS; i++) {
            semList.add(new SemesterLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("9.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")));
        }
        return semList;
    }

    public static ReadOnlyGradTrak getSampleGradTrak() {
        GradTrak sampleAb = new GradTrak();
        for (ModuleTaken sampleModuleTaken : getSampleModulesTaken()) {
            sampleAb.addModuleTaken(sampleModuleTaken);
        }
        sampleAb.setSemesterLimits(getSampleSemesterLimits());
        sampleAb.setCurrentSemester(Semester.Y1S1);
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
