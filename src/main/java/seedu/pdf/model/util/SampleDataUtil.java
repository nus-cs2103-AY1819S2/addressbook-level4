package seedu.pdf.model.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.pdf.model.PdfBook;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.Size;
import seedu.pdf.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PdfBook} with sample data.
 */
public class SampleDataUtil {
    public static final String SAMPLE_DEADLINE_1 = "2019-04-10/READY";
    public static final String SAMPLE_DEADLINE_2 = "2019-04-09/COMPLETE";
    public static Pdf[] getSamplePdfs() {
        Path sampleA = Paths.get("src", "test", "data", "SampleFiles", "NormalFiles",
                "CS2103T_PDF++_UG_Intro.pdf");
        Path sampleB = Paths.get("src", "test", "data", "SampleFiles", "NormalFiles",
                "CS2103T_Week9_Integration Approaches.pdf");
        return new Pdf[] {
            new Pdf(new Name(sampleA.getFileName().toString()),
                    new Directory(sampleA.getParent().toAbsolutePath().toString()),
                    new Size(Long.toString(sampleA.toFile().length())), getTagSet("Lecture", "Week1"),
                    new Deadline(SAMPLE_DEADLINE_1)),
            new Pdf(new Name(sampleB.getFileName().toString()),
                    new Directory(sampleB.getParent().toAbsolutePath().toString()),
                    new Size(Long.toString(sampleB.toFile().length())), getTagSet("Lecture", "Week9"),
                    new Deadline(SAMPLE_DEADLINE_2))
        };
    }

    public static ReadOnlyPdfBook getSamplePdfBook() {
        PdfBook samplePb = new PdfBook();
        return samplePb;
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
