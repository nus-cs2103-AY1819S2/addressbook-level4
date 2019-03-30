package seedu.address.model.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.PdfBook;
import seedu.address.model.ReadOnlyPdfBook;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PdfBook} with sample data.
 */
public class SampleDataUtil {
    public static final String SAMPLE_DEADLINE_1 = "2019-04-10/READY";
    public static final String SAMPLE_DEADLINE_2 = "2019-04-09/COMPLETE";
    public static Pdf[] getSamplePdfs() {
        Path sampleA = Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                "CS2103T_PDF++_UG_Intro.pdf");
        Path sampleB = Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
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
        PdfBook sampleAb = new PdfBook();
        for (Pdf samplePdf : getSamplePdfs()) {
            sampleAb.addPdf(samplePdf);
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
