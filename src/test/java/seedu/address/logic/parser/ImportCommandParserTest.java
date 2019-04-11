/* @@author Carrein */
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.Album;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();
    private Album album = Album.getInstance();

    @Before
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void parse_validFiles_success() {
        String validBmpTest = "src/main/resources/imageTest/valid/validBMPTest.bmp";
        String validDuplicateTest = "src/main/resources/imageTest/valid/validDuplicateTest.jpg";
        String validGifTest = "src/main/resources/imageTest/valid/validGIFTest.gif";
        String validJpgTest = "src/main/resources/imageTest/valid/validJPGTest.jpg";
        String validMimeTest = "src/main/resources/imageTest/valid/validMIMETest.png";
        String validNoMimeTest = "src/main/resources/imageTest/valid/validNOMIMETest.png";
        String validPngTest = "src/main/resources/imageTest/valid/validPNGTest.png";
        String validTifTest = "src/main/resources/imageTest/valid/validTIFTest.tif";
        String validUnreasonablySmallTest = "src/main/resources/imageTest/valid/validUnreasonablySmallTest.jpg";

        assertParseSuccess(parser, validBmpTest, new ImportCommand(false));
        assertParseSuccess(parser, validDuplicateTest, new ImportCommand(false));
        assertParseSuccess(parser, validGifTest, new ImportCommand(false));
        assertParseSuccess(parser, validJpgTest, new ImportCommand(false));
        assertParseSuccess(parser, validMimeTest, new ImportCommand(false));
        assertParseSuccess(parser, validNoMimeTest, new ImportCommand(false));
        assertParseSuccess(parser, validPngTest, new ImportCommand(false));
        assertParseSuccess(parser, validTifTest, new ImportCommand(false));
        assertParseSuccess(parser, validUnreasonablySmallTest, new ImportCommand(false));
    }

    @Test
    public void parse_sampleDir_success() {
        String samplePath = "sample";
        assertParseSuccess(parser, samplePath, new ImportCommand(true));
    }

    @Test
    public void parse_validDir_success() {
        String dirPath = "src/main/resources/imageTest/valid/";
        assertParseSuccess(parser, dirPath, new ImportCommand(true));
    }

    @Test
    public void parse_invalidDir_success() {
        String dirPath = "src/main/resources/imageTest/invalid/";
        assertParseSuccess(parser, dirPath, new ImportCommand(true));
    }

    @Test
    public void parse_emptyPath_failure() {
        String emptyPath = "";
        assertParseFailure(parser, emptyPath, Messages.MESSAGE_INVALID_PATH);
    }

    @Test
    public void parse_invalidPath_failure() {
        String emptyPath = "src/main/resources/imageTest/invalid/invalidPath.txt";
        assertParseFailure(parser, emptyPath, Messages.MESSAGE_INVALID_PATH);
    }

    @Test
    public void parse_invalidFormat_failure() {
        String invalidPdf = "src/main/resources/imageTest/invalid/invalidPDF.pdf";
        String invalidRar = "src/main/resources/imageTest/invalid/invalidRARTest.rar";
        String invalidText = "src/main/resources/imageTest/invalid/invalidSampleTextTest.txt";
        String invalidZip = "src/main/resources/imageTest/invalid/invalidZIPTest.zip";

        List<String> invalidFiles = new ArrayList<>();
        invalidFiles.add(invalidPdf);
        invalidFiles.add(invalidRar);
        invalidFiles.add(invalidText);
        invalidFiles.add(invalidZip);

        for (String s : invalidFiles) {
            assertParseFailure(parser, s, Messages.MESSAGE_INVALID_FORMAT);
        }
    }

    @Test
    public void parse_invalidHidden_failure() {
        String invalidText = "src/main/resources/imageTest/invalid/.invalidHiddenTest.txt";
        String invalidImage = "src/main/resources/imageTest/invalid/.invalidHiddenImageTest.jpg";

        List<String> hiddenFiles = new ArrayList<>();
        hiddenFiles.add(invalidText);
        hiddenFiles.add(invalidImage);

        for (String s : hiddenFiles) {
            assertParseFailure(parser, s, Messages.MESSAGE_INVALID_FORMAT);
        }
    }

    @Test
    public void parse_invalidSize_failure() {
        String emptyPath = "src/main/resources/imageTest/invalid/invalidUnreasonableBigTest.jpg";
        assertParseFailure(parser, emptyPath, Messages.MESSAGE_INVALID_SIZE);
    }

    @Test
    public void parse_invalidMagicNumber_failure() {
        String emptyPath = "src/main/resources/imageTest/invalid/invalidMagicNumberPDFtoPNG.png";
        assertParseFailure(parser, emptyPath, Messages.MESSAGE_UNABLE_TO_READ_FILE);
    }

    @Test
    public void parse_invalidDuplicate_failure() {
        String firstFile = "src/main/resources/imageTest/valid/validDuplicateTest.jpg";

        assertParseSuccess(parser, firstFile, new ImportCommand(false));
        assertParseFailure(parser, firstFile, Messages.MESSAGE_DUPLICATE_FILE);
    }
}
