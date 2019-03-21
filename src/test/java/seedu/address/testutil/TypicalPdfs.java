package seedu.address.testutil;

//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
    private static final Path a_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "a.pdf");
    private static final Path b_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "b.pdf");
    private static final Path c_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "c.pdf");
    private static final Path d_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "d.pdf");
    private static final Path e_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "e.pdf");
    private static final Path f_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "f.pdf");
    private static final Path g_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "g.pdf");
    private static final Path h_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "h.pdf");
    private static final Path i_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "i.pdf");
    private static final Path j_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "j.pdf");
    private static final Path k_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "k.pdf");

    //Duplicates - Pdfs that have the same name but in a different location
    private static final Path a_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "a.pdf");
    private static final Path b_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "b.pdf");
    private static final Path c_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "c.pdf");
    private static final Path d_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "d.pdf");
    private static final Path e_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "e.pdf");
    private static final Path f_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "f.pdf");
    private static final Path g_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "g.pdf");
    private static final Path h_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "h.pdf");
    private static final Path i_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "i.pdf");
    private static final Path j_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "j.pdf");
    private static final Path k_dup_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "Duplicates",
            "k.pdf");

    //Originals
    public static final Pdf A_PDF = new PdfBuilder().withName(a_path.toFile().getName())
            .withLocation(a_path.toAbsolutePath().toString())
            .withSize(Long.toString(a_path.toFile().length()))
            .withTags("moduleA", "admin", "w1")
            .build();
    public static final Pdf B_PDF = new PdfBuilder().withName(b_path.toFile().getName())
            .withLocation(b_path.toAbsolutePath().toString())
            .withTags("moduleB", "lecture")
            .withSize(Long.toString(b_path.toFile().length())).build();
    public static final Pdf C_PDF = new PdfBuilder().withName(c_path.toFile().getName())
            .withLocation(c_path.toAbsolutePath().toString())
            .withTags("moduleC", "tutorial", "w7")
            .withSize(Long.toString(c_path.toFile().length())).build();
    public static final Pdf D_PDF = new PdfBuilder().withName(d_path.toFile().getName())
            .withLocation(d_path.toAbsolutePath().toString())
            .withTags("moduleD", "tutorial", "w7")
            .withSize(Long.toString(d_path.toFile().length())).build();
    public static final Pdf E_PDF = new PdfBuilder().withName(e_path.toFile().getName())
            .withLocation(e_path.toAbsolutePath().toString())
            .withTags("moduleE", "tutorial", "w7")
            .withSize(Long.toString(e_path.toFile().length())).build();
    public static final Pdf F_PDF = new PdfBuilder().withName(f_path.toFile().getName())
            .withLocation(f_path.toAbsolutePath().toString())
            .withTags("moduleF", "tutorial", "w7")
            .withSize(Long.toString(f_path.toFile().length())).build();
    public static final Pdf G_PDF = new PdfBuilder().withName(g_path.toFile().getName())
            .withLocation(g_path.toAbsolutePath().toString())
            .withTags("moduleG", "tutorial", "w7")
            .withSize(Long.toString(g_path.toFile().length())).build();
    public static final Pdf H_PDF = new PdfBuilder().withName(h_path.toFile().getName())
            .withLocation(h_path.toAbsolutePath().toString())
            .withTags("moduleH", "tutorial", "w7")
            .withSize(Long.toString(h_path.toFile().length())).build();
    public static final Pdf I_PDF = new PdfBuilder().withName(i_path.toFile().getName())
            .withLocation(i_path.toAbsolutePath().toString())
            .withTags("moduleI", "tutorial", "w7")
            .withSize(Long.toString(i_path.toFile().length())).build();
    public static final Pdf J_PDF = new PdfBuilder().withName(j_path.toFile().getName())
            .withLocation(j_path.toAbsolutePath().toString())
            .withTags("moduleJ", "tutorial", "w7")
            .withSize(Long.toString(j_path.toFile().length())).build();
    public static final Pdf K_PDF = new PdfBuilder().withName(k_path.toFile().getName())
            .withLocation(k_path.toAbsolutePath().toString())
            .withTags("moduleK", "tutorial", "w7")
            .withSize(Long.toString(k_path.toFile().length())).build();

    //Duplicates
    public static final Pdf A_DUP_PDF = new PdfBuilder().withName(a_dup_path.toFile().getName())
            .withLocation(a_dup_path.toAbsolutePath().toString())
            .withSize(Long.toString(a_dup_path.toFile().length()))
            .withTags("moduleA", "admin", "w1")
            .build();
    public static final Pdf B_DUP_PDF = new PdfBuilder().withName(b_dup_path.toFile().getName())
            .withLocation(b_dup_path.toAbsolutePath().toString())
            .withTags("moduleB", "lecture")
            .withSize(Long.toString(b_dup_path.toFile().length())).build();
    public static final Pdf C_DUP_PDF = new PdfBuilder().withName(c_dup_path.toFile().getName())
            .withLocation(c_dup_path.toAbsolutePath().toString())
            .withTags("moduleC", "tutorial", "w7")
            .withSize(Long.toString(c_dup_path.toFile().length())).build();
    public static final Pdf D_DUP_PDF = new PdfBuilder().withName(d_dup_path.toFile().getName())
            .withLocation(d_dup_path.toAbsolutePath().toString())
            .withTags("moduleD", "tutorial", "w7")
            .withSize(Long.toString(d_dup_path.toFile().length())).build();
    public static final Pdf E_DUP_PDF = new PdfBuilder().withName(e_dup_path.toFile().getName())
            .withLocation(e_dup_path.toAbsolutePath().toString())
            .withTags("moduleE", "tutorial", "w7")
            .withSize(Long.toString(e_dup_path.toFile().length())).build();
    public static final Pdf F_DUP_PDF = new PdfBuilder().withName(f_dup_path.toFile().getName())
            .withLocation(f_dup_path.toAbsolutePath().toString())
            .withTags("moduleF", "tutorial", "w7")
            .withSize(Long.toString(f_dup_path.toFile().length())).build();
    public static final Pdf G_DUP_PDF = new PdfBuilder().withName(g_dup_path.toFile().getName())
            .withLocation(g_dup_path.toAbsolutePath().toString())
            .withTags("moduleG", "tutorial", "w7")
            .withSize(Long.toString(g_dup_path.toFile().length())).build();
    public static final Pdf H_DUP_PDF = new PdfBuilder().withName(h_dup_path.toFile().getName())
            .withLocation(h_dup_path.toAbsolutePath().toString())
            .withTags("moduleH", "tutorial", "w7")
            .withSize(Long.toString(h_dup_path.toFile().length())).build();
    public static final Pdf I_DUP_PDF = new PdfBuilder().withName(i_dup_path.toFile().getName())
            .withLocation(i_dup_path.toAbsolutePath().toString())
            .withTags("moduleI", "tutorial", "w7")
            .withSize(Long.toString(i_dup_path.toFile().length())).build();
    public static final Pdf J_DUP_PDF = new PdfBuilder().withName(j_dup_path.toFile().getName())
            .withLocation(j_dup_path.toAbsolutePath().toString())
            .withTags("moduleJ", "tutorial", "w7")
            .withSize(Long.toString(j_dup_path.toFile().length())).build();
    public static final Pdf K_DUP_PDF = new PdfBuilder().withName(k_dup_path.toFile().getName())
            .withLocation(k_dup_path.toAbsolutePath().toString())
            .withTags("moduleK", "tutorial", "w7")
            .withSize(Long.toString(k_dup_path.toFile().length())).build();

    /*public static final Pdf ALICE = new PdfBuilder().withName("Alice Pauline")
            .withLocation("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSize("94351253")
            .withTags("friends").build();
    public static final Pdf BENSON = new PdfBuilder().withName("Benson Meier")
            .withLocation("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withSize("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Pdf CARL = new PdfBuilder().withName("Carl Kurz").withSize("95352563")
            .withEmail("heinz@example.com").withLocation("wall street").build();
    public static final Pdf DANIEL = new PdfBuilder().withName("Daniel Meier").withSize("87652533")
            .withEmail("cornelia@example.com").withLocation("10th street").withTags("friends").build();
    public static final Pdf ELLE = new PdfBuilder().withName("Elle Meyer").withSize("9482224")
            .withEmail("werner@example.com").withLocation("michegan ave").build();
    public static final Pdf FIONA = new PdfBuilder().withName("Fiona Kunz").withSize("9482427")
            .withEmail("lydia@example.com").withLocation("little tokyo").build();
    public static final Pdf GEORGE = new PdfBuilder().withName("George Best").withSize("9482442")
            .withEmail("anna@example.com").withLocation("4th street").build();

    // Manually added
    public static final Pdf HOON = new PdfBuilder().withName("Hoon Meier").withSize("8482424")
            .withEmail("stefan@example.com").withLocation("little india").build();
    public static final Pdf IDA = new PdfBuilder().withName("Ida Mueller").withSize("8482131")
            .withEmail("hans@example.com").withLocation("chicago ave").build();

    // Manually added - Pdf's details found in {@code CommandTestUtil}
    public static final Pdf AMY = new PdfBuilder().withName(VALID_NAME_AMY).withSize(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withLocation(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Pdf BOB = new PdfBuilder().withName(VALID_NAME_BOB).withSize(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER*/

    private TypicalPdfs() {} // prevents instantiation

    /**
     * Returns an {@code PdfBook} with all the typical persons.
     */
    public static PdfBook getTypicalAddressBook() {
        PdfBook ab = new PdfBook();
        for (Pdf pdf : getTypicalPersons()) {
            ab.addPdf(pdf);
        }
        return ab;
    }

    public static List<Pdf> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(A_PDF, B_PDF, C_PDF));
    }
}
