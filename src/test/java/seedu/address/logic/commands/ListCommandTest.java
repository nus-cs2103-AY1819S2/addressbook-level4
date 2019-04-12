package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.BattleshipTest;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
    private static final Coordinates VALID_COORDINATES_A1 = new Coordinates("a1");
    private static final Coordinates VALID_COORDINATES_A2 = new Coordinates("a2");
    private static final Coordinates VALID_COORDINATES_A3 = new Coordinates("a3");

    private static final Orientation HORIZONTAL_ORIENTATION = new Orientation("h");
    private static final Orientation VERTICAL_ORIENTATION = new Orientation("v");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private Logic logic;
    private CommandHistory commandHistory = new CommandHistory();

    private Set<Tag> testTagSet1 = new HashSet<>(); // to contain testTag1, testTag2
    private Set<Tag> testTagSet2 = new HashSet<>(); // to contain testTag1 only
    private Set<Tag> emptyTagSet = new HashSet<>(); // to contain no tags

    private Tag testTag1 = new Tag("testTag1");
    private Tag testTag2 = new Tag("testTag2");

    private Battleship battleshipOne;
    private Battleship battleshipTwo;
    private Battleship battleshipThree;

    @Before
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        // set the tests in the context of deploying ships
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        expectedModel.setBattleState(BattleState.PLAYER_PUT_SHIP);

        testTagSet1.add(testTag1);
        testTagSet1.add(testTag2);

        testTagSet2.add(testTag1);

        battleshipOne = new DestroyerBattleship(testTagSet1);
        battleshipTwo = new CruiserBattleship(testTagSet2);
        battleshipThree = new DestroyerBattleship(emptyTagSet);

        model.deployBattleship(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION);
        model.deployBattleship(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION);
        model.deployBattleship(battleshipThree, VALID_COORDINATES_A3, VERTICAL_ORIENTATION);
    }

    @Test
    public void execute_listAll_success() {
        // The command is: list
        ListCommand listCommand = new ListCommand(Optional.empty(), Optional.empty());

        StringBuilder expectedOutput = new StringBuilder();

        expectedOutput.append(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION))
                .append("\n")
                .append(buildOutput(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION))
                .append("\n")
                .append(buildOutput(battleshipThree, VALID_COORDINATES_A3, VERTICAL_ORIENTATION))
                .append("\n");

        CommandResult expectedCommandResult = new CommandResult(expectedOutput.toString(), false, false);
        CommandResult command = listCommand.execute(model, commandHistory);

        assertCommandSuccess(listCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * For building the output in list.
     */
    private String buildOutput(Battleship battleship, Coordinates coordinates, Orientation orientation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(battleship)
                .append(" (")
                .append(battleship.getLife())
                .append("/")
                .append(battleship.getLength())
                .append(")")
                .append(" at ")
                .append(coordinates)
                .append(" ")
                .append(orientation)
                .append(" ");
        battleship.getTags().forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
