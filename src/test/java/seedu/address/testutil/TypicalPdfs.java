package seedu.address.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PdfBook;
import seedu.address.model.pdf.Pdf;

/**
 * A utility class containing a list of {@code Pdf} objects to be used in tests.
 */
public class TypicalPdfs {

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    // Original Pdfs
    private static final Path sample_pdf_1_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2103T_PDF++_UG_Intro.pdf");
    private static final Path sample_pdf_2_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2103T_Week9_Integration Approaches.pdf");
    private static final Path sample_pdf_3_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS3230_Lecture9_GreedyAlgorithm.pdf");
    private static final Path sample_pdf_4_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2103T_Lecture3.pdf");
    private static final Path sample_pdf_5_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2103_Schedule_AY1819S2.pdf");
    private static final Path sample_pdf_6_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "GitCheatSheet.pdf");
    private static final Path sample_pdf_7_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "Markdown-Cheatsheet.pdf");
    private static final Path sample_pdf_8_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2101_UG and DG Peer Review.pdf");
    private static final Path sample_pdf_9_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2101_Product Demo strategies1.pdf");
    private static final Path sample_pdf_10_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2103T_sample PPP.pdf");
    private static final Path sample_pdf_11_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "CS2101_Writing User Guides.pdf");
    private static final Path sample_invalidPdf_1_path =
            Paths.get("src", "test", "data", "JsonAdaptedPdfTest",
                    "invalidPathToFile.pdf");

    //Duplicates - Pdfs that have the same name but in a different location
    private static final Path sample_pdf_1_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2103T_PDF++_UG_Intro.pdf.pdf");
    private static final Path sample_pdf_2_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2103T_Week9_Integration Approaches.pdf");
    private static final Path sample_pdf_3_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS3230_Lecture9_GreedyAlgorithm.pdf");
    private static final Path sample_pdf_4_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2103T_Lecture3.pdf");
    private static final Path sample_pdf_5_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2103_Schedule_AY1819S2.pdf");
    private static final Path sample_pdf_6_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "GitCheatSheet.pdf");
    private static final Path sample_pdf_7_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "Markdown-Cheatsheet.pdf");
    private static final Path sample_pdf_8_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2101_UG and DG Peer Review.pdf");
    private static final Path sample_pdf_9_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2101_Product Demo strategies1.pdf");
    private static final Path sample_pdf_10_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2103T_sample PPP.pdf");
    private static final Path sample_pdf_11_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "CS2101_Writing User Guides.pdf");
    private static final Path sample_invalidPdf_1_duplicate_path =
            Paths.get("test", "data", "JsonAdaptedPdfTest",
                    "Duplicates", "invalidPdf_1.pdf");

    //Originals
    public static final Pdf SAMPLE_PDF_1 = new PdfBuilder().withName(sample_pdf_1_path.toFile().getName())
            .withDirectory(sample_pdf_1_path.toAbsolutePath().getParent().toString())
            .withSize(Long.toString(sample_pdf_1_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_2 = new PdfBuilder().withName(sample_pdf_2_path.toFile().getName())
            .withDirectory(sample_pdf_2_path.toAbsolutePath().getParent().toString())
            .withTags("CS2103T", "lecture", "w9")
            .withSize(Long.toString(sample_pdf_2_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_3 = new PdfBuilder().withName(sample_pdf_3_path.toFile().getName())
            .withDirectory(sample_pdf_3_path.toAbsolutePath().getParent().toString())
            .withTags("CS3230", "lecture", "w9")
            .withSize(Long.toString(sample_pdf_3_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_4 = new PdfBuilder().withName(sample_pdf_4_path.toFile().getName())
            .withDirectory(sample_pdf_4_path.toAbsolutePath().getParent().toString())
            .withTags("CS2103T", "lecture", "w3")
            .withSize(Long.toString(sample_pdf_4_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_5 = new PdfBuilder().withName(sample_pdf_5_path.toFile().getName())
            .withDirectory(sample_pdf_5_path.toAbsolutePath().getParent().toString())
            .withTags("CS2103", "schedule")
            .withSize(Long.toString(sample_pdf_5_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_6 = new PdfBuilder().withName(sample_pdf_6_path.toFile().getName())
            .withDirectory(sample_pdf_6_path.toAbsolutePath().getParent().toString())
            .withTags("Git", "cheatsheet")
            .withSize(Long.toString(sample_pdf_6_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_7 = new PdfBuilder().withName(sample_pdf_7_path.toFile().getName())
            .withDirectory(sample_pdf_7_path.toAbsolutePath().getParent().toString())
            .withTags("markdown", "cheatsheet")
            .withSize(Long.toString(sample_pdf_7_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_8 = new PdfBuilder().withName(sample_pdf_8_path.toFile().getName())
            .withDirectory(sample_pdf_8_path.toAbsolutePath().getParent().toString())
            .withTags("CS2101", "review")
            .withSize(Long.toString(sample_pdf_8_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_9 = new PdfBuilder().withName(sample_pdf_9_path.toFile().getName())
            .withDirectory(sample_pdf_9_path.toAbsolutePath().getParent().toString())
            .withTags("CS2101", "demo", "strategies")
            .withSize(Long.toString(sample_pdf_9_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_10 = new PdfBuilder().withName(sample_pdf_10_path.toFile().getName())
            .withDirectory(sample_pdf_10_path.toAbsolutePath().getParent().toString())
            .withTags("CS2103T", "PPP")
            .withSize(Long.toString(sample_pdf_10_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_11 = new PdfBuilder().withName(sample_pdf_11_path.toFile().getName())
            .withDirectory(sample_pdf_11_path.toAbsolutePath().getParent().toString())
            .withTags("CS2101", "writing", "UG")
            .withSize(Long.toString(sample_pdf_11_path.toFile().length())).build();
    public static final Pdf SAMPLE_INVALIDPDF_1 = new PdfBuilder().withName(sample_invalidPdf_1_path.toFile().getName())
            .withDirectory(sample_invalidPdf_1_path.toAbsolutePath().getParent().toString())
            .withTags("invalid", "pdf")
            .withSize(Long.toString(sample_invalidPdf_1_path.toFile().length())).build();

    //Duplicates
    public static final Pdf SAMPLE_PDF_1_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_1_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_1_duplicate_path.toAbsolutePath().toString())
            .withSize(Long.toString(sample_pdf_1_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_2_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_2_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_2_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2103T", "lecture", "w9")
            .withSize(Long.toString(sample_pdf_2_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_3_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_3_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_3_duplicate_path.toAbsolutePath().toString())
            .withTags("CS3230", "lecture", "w9")
            .withSize(Long.toString(sample_pdf_3_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_4_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_4_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_4_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2103T", "lecture", "w3")
            .withSize(Long.toString(sample_pdf_4_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_5_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_5_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_5_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2103", "schedule")
            .withSize(Long.toString(sample_pdf_5_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_6_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_6_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_6_duplicate_path.toAbsolutePath().toString())
            .withTags("Git", "cheatsheet")
            .withSize(Long.toString(sample_pdf_6_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_7_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_7_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_7_duplicate_path.toAbsolutePath().toString())
            .withTags("markdown", "cheatsheet")
            .withSize(Long.toString(sample_pdf_7_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_8_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_8_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_8_duplicate_path.toAbsolutePath().toString())
            .withTags("moduleH", "tutorial", "w7")
            .withSize(Long.toString(sample_pdf_8_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_9_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_9_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_9_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2101", "review")
            .withSize(Long.toString(sample_pdf_9_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_10_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_10_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_10_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2103T", "PPP")
            .withSize(Long.toString(sample_pdf_10_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_PDF_11_DUPLICATE = new PdfBuilder()
            .withName(sample_pdf_11_duplicate_path.toFile().getName())
            .withDirectory(sample_pdf_11_duplicate_path.toAbsolutePath().toString())
            .withTags("CS2101", "writing", "UG")
            .withSize(Long.toString(sample_pdf_11_duplicate_path.toFile().length())).build();
    public static final Pdf SAMPLE_INVALIDPDF_1_DUPLICATE = new PdfBuilder()
            .withName(sample_invalidPdf_1_duplicate_path.toFile().getName())
            .withDirectory(sample_invalidPdf_1_duplicate_path.toAbsolutePath().toString())
            .withTags("invalid", "pdf")
            .withSize(Long.toString(sample_invalidPdf_1_duplicate_path.toFile().length())).build();

    private TypicalPdfs() {} // prevents instantiation

    /**
     * Returns an {@code PdfBook} with all the typical persons.
     */
    public static PdfBook getTypicalPdfBook() {
        PdfBook pdfBook = new PdfBook();
        for (Pdf pdf : getTypicalPdfs()) {
            pdfBook.addPdf(pdf);
        }
        return pdfBook;
    }

    public static List<Pdf> getTypicalPdfs() {
        return new ArrayList<>(Arrays.asList(SAMPLE_PDF_1, SAMPLE_PDF_2, SAMPLE_PDF_3));
    }
}
