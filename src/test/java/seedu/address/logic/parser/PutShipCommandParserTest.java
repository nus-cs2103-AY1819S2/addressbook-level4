package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COORDINATE_FIRST_ROW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COORDINATE_DESC_OUT_OF_BOUNDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COORDINATE_DESC_SYMBOLS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DESTROYER;
import static seedu.address.logic.commands.CommandTestUtil.ORIENTATION_HORIZONTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATES_A1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATES_J1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HORIZONTAL_ORIENTATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.PutShipCommand;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

public class PutShipCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PutShipCommand.MESSAGE_USAGE);

    private PutShipCommandParser parser = new PutShipCommandParser();

    private final Set<Tag> emptySet = new HashSet<>();

    @Test
    public void parse_missingParts_failure() {
        // missing field coordinates
        assertParseFailure(parser, NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // missing field name
        assertParseFailure(parser, COORDINATE_FIRST_ROW, MESSAGE_INVALID_FORMAT);

        // no fields specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_COORDINATES_J1,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);

        // invalid coordinates and missing name
        assertParseFailure(parser, INVALID_COORDINATE_DESC_SYMBOLS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INVALID_COORDINATE_DESC_OUT_OF_BOUNDS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);

        // invalid name followed by valid coordinates
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_COORDINATE_DESC_SYMBOLS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_COORDINATE_DESC_OUT_OF_BOUNDS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);

        // valid name followed by invalid coordinates.
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_COORDINATE_DESC_SYMBOLS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_COORDINATE_DESC_OUT_OF_BOUNDS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_COORDINATE_DESC_SYMBOLS,
                "Invalid command format! \n" + PutShipCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_DESC_DESTROYER + COORDINATE_FIRST_ROW + ORIENTATION_HORIZONTAL;

        Battleship battleship = new DestroyerBattleship(emptySet);
        Coordinates coordinates = new Coordinates(VALID_COORDINATES_A1);
        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);

        PutShipCommand expectedCommand = new PutShipCommand(coordinates, battleship, orientation);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
