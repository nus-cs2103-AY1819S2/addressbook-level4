/* @@author itszp */
package seedu.address.logic.parser;

import static seedu.address.commons.core.Config.VALID_FILE_TYPES;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SAVE_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SAVE_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SaveCommand;

public class SaveCommandParserTest {
    @Test
    public void parse_emptyString_success() {
        SaveCommandParser parser = new SaveCommandParser();
        String emptyString = "";
        assertParseSuccess(parser, emptyString, new SaveCommand(""));
    }

    @Test
    public void parse_validSaveNames_success() {
        String validJpg = "sample.jpg";
        String validJpeg = "sample.jpeg";
        String validPng = "sample.png";
        String validGif = "sample.gif";
        String validBmp = "sample.bmp";
        String validTif = "sample.tif";
        String validTiff = "sample.tiff";

        SaveCommandParser parser = new SaveCommandParser();
        assertParseSuccess(parser, validJpg, new SaveCommand("sample.jpg"));
        assertParseSuccess(parser, validJpeg, new SaveCommand("sample.jpeg"));
        assertParseSuccess(parser, validPng, new SaveCommand("sample.png"));
        assertParseSuccess(parser, validGif, new SaveCommand("sample.gif"));
        assertParseSuccess(parser, validBmp, new SaveCommand("sample.bmp"));
        assertParseSuccess(parser, validTif, new SaveCommand("sample.tif"));
        assertParseSuccess(parser, validTiff, new SaveCommand("sample.tiff"));
    }

    @Test
    public void parse_invalidSaveName_failure() {
        String invalidJpg = ".jpg";
        String invalidJpeg = ".jpeg";
        String invalidPng = ".png";
        String invalidGif = ".gif";
        String invalidBmp = ".bmp";
        String invalidTif = ".tif";

        SaveCommandParser parser = new SaveCommandParser();
        assertParseFailure(parser, invalidJpg, MESSAGE_INVALID_SAVE_NAME);
        assertParseFailure(parser, invalidJpeg, MESSAGE_INVALID_SAVE_NAME);
        assertParseFailure(parser, invalidPng, MESSAGE_INVALID_SAVE_NAME);
        assertParseFailure(parser, invalidGif, MESSAGE_INVALID_SAVE_NAME);
        assertParseFailure(parser, invalidBmp, MESSAGE_INVALID_SAVE_NAME);
        assertParseFailure(parser, invalidTif, MESSAGE_INVALID_SAVE_NAME);
    }

    @Test
    public void parse_invalidSaveType_failure() {
        String invalidNoPeriod = "jpg";
        String invalidPdf = ".pdf";
        String invalidUnknownFormat = ".p";
        String invalidSpelling = ".pngg";

        SaveCommandParser parser = new SaveCommandParser();
        assertParseFailure(parser, invalidNoPeriod, String.format(MESSAGE_INVALID_SAVE_TYPE,
                Arrays.toString(VALID_FILE_TYPES)));
        assertParseFailure(parser, invalidPdf, String.format(MESSAGE_INVALID_SAVE_TYPE,
                Arrays.toString(VALID_FILE_TYPES)));
        assertParseFailure(parser, invalidUnknownFormat, String.format(MESSAGE_INVALID_SAVE_TYPE,
                Arrays.toString(VALID_FILE_TYPES)));
        assertParseFailure(parser, invalidSpelling, String.format(MESSAGE_INVALID_SAVE_TYPE,
                Arrays.toString(VALID_FILE_TYPES)));
    }
}
