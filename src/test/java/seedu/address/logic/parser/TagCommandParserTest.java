package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PdfBuilder;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;

public class TagCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        /*Pdf expectedPdf = new PdfBuilder(SAMPLE_PDF_1).withTags(VALID_TAG_LECTURE).build();

        // multiple tags - all accepted
        Pdf expectedPdfMultipleTags = new PdfBuilder(SAMPLE_PDF_1).withTags(VALID_TAG_CS2103T, VALID_TAG_LECTURE)
                .build();
        assertParseSuccess(parser, FILE_DESC_1_PDF + TAG_DESC_LECTURE + TAG_DESC_CS2103T,
                new TagCommand(expectedPdfMultipleTags));*/
    }

}
