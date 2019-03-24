package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Tag> tagSet = new HashSet<>();
        assertParseSuccess(parser, "listTags", new ListCommand(tagSet));

        tagSet.add(new Tag("amazing"));
        assertParseSuccess(parser, "listTags t/amazing", new ListCommand(tagSet));
    }
}
