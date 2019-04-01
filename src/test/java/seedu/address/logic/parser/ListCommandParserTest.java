package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.battleship.Name;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Tag> tagSet = new HashSet<>();
        Name name = new Name("destroyer");

        Optional optionalTagSet = Optional.of(tagSet);
        Optional optionalName = Optional.of(name);

        assertParseSuccess(parser, "list", new ListCommand(Optional.empty(), Optional.empty()));
        tagSet.add(new Tag("amazing"));
        assertParseSuccess(parser, "listTags t/amazing", new ListCommand(optionalTagSet, Optional.empty()));
        assertParseSuccess(parser, "listTags t/amazing n/destroyer",
                new ListCommand(optionalTagSet, optionalName));
    }
}
