package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Tag> tagSet = new HashSet<>();
        Set<Name> nameSet = new HashSet<>();

        Optional<Set<Tag>> optionalTagSet = Optional.of(tagSet);
        Optional<Set<Name>> optionalNameSet = Optional.of(nameSet);

        assertParseSuccess(parser, "list", new ListCommand(Optional.empty(), Optional.empty()));
        tagSet.add(new Tag("amazing"));
        nameSet.add(Battleship.BattleshipType.DESTROYER.getName());
        assertParseSuccess(parser, "listTags t/amazing", new ListCommand(Optional.empty(), optionalTagSet));
        assertParseSuccess(parser, "listTags t/amazing n/destroyer",
                new ListCommand(optionalNameSet, optionalTagSet));
    }
}
