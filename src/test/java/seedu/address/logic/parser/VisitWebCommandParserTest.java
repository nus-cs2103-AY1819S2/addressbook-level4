package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.util.WebUtil;
import seedu.address.logic.commands.VisitWebCommand;
import seedu.address.model.restaurant.Weblink;

public class VisitWebCommandParserTest {

    private static final String VALID_WEBLINK = "https://www.google.com.sg";

    private VisitWebCommandParser parser = new VisitWebCommandParser();

    @Test
    public void parse_validIndex_returnsVisitWebCommand() {
        assertParseSuccess(parser, "1", new VisitWebCommand(INDEX_FIRST_RESTAURANT));
    }

    @Test
    public void parse_validWeblink_returnsVisitWebCommand() {
        Weblink validWeblink = new Weblink(VALID_WEBLINK);
        assertParseSuccess(parser, VALID_WEBLINK, new VisitWebCommand(validWeblink));
    }

    @Test
    public void parse_invalidWeblink_throwsParseException() {
        assertParseFailure(parser, "a", Weblink.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_weblinkNotFound_throwsParseException() {
        String invalidWeblink = "www.aasdasd.com";
        assertParseFailure(parser, invalidWeblink, String.format(WebUtil.INVALID_URL_MESSAGE, invalidWeblink));
    }
}
