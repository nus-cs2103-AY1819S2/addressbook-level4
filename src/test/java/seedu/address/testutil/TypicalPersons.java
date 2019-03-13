package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
public class TypicalPersons {

    public static final Path a_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "a.pdf").toFile().isd;
    public static final Path b_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "b.pdf");
    public static final Path c_path = Paths.get("test", "data", "JsonAdaptedPdfTest", "c.pdf");
    public static final Pdf A_PDF = new PdfBuilder().withName(a_path.toFile().getName())
            .withLocation(a_path.toString())
            .withSize(Long.toString(a_path.toFile().getTotalSpace())).build();
    public static final Pdf B_PDF = new PdfBuilder().withName(b_path.toFile().getName())
            .withLocation(b_path.toString())
            .withSize(Long.toString(b_path.toFile().getTotalSpace())).build();
    public static final Pdf C_PDF = new PdfBuilder().withName(c_path.toFile().getName())
            .withLocation(c_path.toString())
            .withSize(Long.toString(c_path.toFile().getTotalSpace())).build();

    public static final Pdf ALICE = new PdfBuilder().withName("Alice Pauline")
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

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

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
