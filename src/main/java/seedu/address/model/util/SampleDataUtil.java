package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.GradTrak;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.moduleinfo.ModuleInfoCode;
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
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("friends")),
            new ModuleTaken(new ModuleInfoCode("CS2101"), Semester.valueOf("Y1S1"), Grade.valueOf("C"),
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("colleagues", "friends")),
            new ModuleTaken(new ModuleInfoCode("CS1010"), Semester.valueOf("Y2S1"), Grade.valueOf("C"),
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("neighbours")),
            new ModuleTaken(new ModuleInfoCode("LSM1301"), Semester.valueOf("Y2S2"), Grade.valueOf("C"),
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("family")),
            new ModuleTaken(new ModuleInfoCode("GER1000"), Semester.valueOf("Y2S1"), Grade.valueOf("C"),
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("classmates")),
            new ModuleTaken(new ModuleInfoCode("MA1521"), Semester.valueOf("Y2S2"), Grade.valueOf("C"),
                Grade.valueOf("A"), new Hour("0"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyGradTrak getSampleGradTrak() {
        GradTrak sampleAb = new GradTrak();
        for (ModuleTaken sampleModuleTaken : getSampleModulesTaken()) {
            sampleAb.addModuleTaken(sampleModuleTaken);
        }
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
