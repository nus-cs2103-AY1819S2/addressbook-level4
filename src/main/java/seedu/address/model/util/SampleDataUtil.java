package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.GradTrak;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.SemLimit;
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
    public static ModuleTaken[] getSampleModulesTaken() {
        return new ModuleTaken[] {
            new ModuleTaken(new ModuleInfoCode("CS2103T"), Semester.valueOf("Y1S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("friends")),
            new ModuleTaken(new ModuleInfoCode("CS2101"), Semester.valueOf("Y1S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("colleagues", "friends")),
            new ModuleTaken(new ModuleInfoCode("CS1010"), Semester.valueOf("Y2S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("neighbours")),
            new ModuleTaken(new ModuleInfoCode("LSM1301"), Semester.valueOf("Y2S2"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("family")),
            new ModuleTaken(new ModuleInfoCode("GER1000"), Semester.valueOf("Y2S1"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("classmates")),
            new ModuleTaken(new ModuleInfoCode("MA1521"), Semester.valueOf("Y2S2"), Grade.valueOf("C"),
                Grade.valueOf("A"),
                    getTagSet("colleagues"))
        };
    }

    public static SemLimit[] getSampleSemesterLimits() {
        return new SemLimit[] {
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0")),
            new SemLimit(new CapAverage(2.0), new CapAverage(5.0), new Hour("5.0"), new Hour("0.0"),
                    new Hour("2.5"), new Hour("5.0"), new Hour("2.0"), new Hour("5.0"), new Hour("2.0"),
                    new Hour("5.0"), new Hour("6.0"), new Hour("10.0"))
        };
    }

    public static ReadOnlyGradTrak getSampleGradTrak() {
        GradTrak sampleAb = new GradTrak();
        for (ModuleTaken sampleModuleTaken : getSampleModulesTaken()) {
            sampleAb.addModuleTaken(sampleModuleTaken);
        }
        for (SemLimit semLimit : getSampleSemesterLimits()) {
            sampleAb.addSemesterLimit(semLimit);
        }
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
