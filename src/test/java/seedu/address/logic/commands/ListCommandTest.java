package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.battle.state.BattleState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Name;
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

    private Set<Tag> testTagSet1 = new HashSet<>(); // to contain testTag1, testTag2 only battleship 1
    private Set<Tag> testTagSet2 = new HashSet<>(); // to contain testTag1 both battleship 1 and 2
    private Set<Tag> emptyTagSet = new HashSet<>(); // to contain no tags all battleships

    private Set<Name> destroyerOnlySet = new HashSet<>(); // to contain only battleship 1
    private Set<Name> cruiserOnlySet = new HashSet<>(); // to contain only battleship 2
    private Set<Name> cruiserAndDestroyerSet = new HashSet<>(); // to contain both battleship 1 and 2

    private Tag testTag1 = new Tag("testTag1");
    private Tag testTag2 = new Tag("testTag2");

    private Battleship battleshipOne;
    private Battleship battleshipTwo;

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

        destroyerOnlySet.add(Battleship.BattleshipType.DESTROYER.getName());
        cruiserOnlySet.add(Battleship.BattleshipType.CRUISER.getName());

        cruiserAndDestroyerSet.add(Battleship.BattleshipType.CRUISER.getName());
        cruiserAndDestroyerSet.add(Battleship.BattleshipType.DESTROYER.getName());

        model.deployBattleship(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION);
        model.deployBattleship(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION);
    }

    @Test
    public void execute_listAll_success() {
        // The command is: list
        ListCommand listCommand = new ListCommand(Optional.empty(), Optional.empty());

        StringBuilder expectedOutput = new StringBuilder();

        expectedOutput.append(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION))
                .append("\n")
                .append(buildOutput(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION))
                .append("\n");

        CommandResult expectedCommandResult = new CommandResult(expectedOutput.toString(), false, false);
        assertCommandSuccess(listCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listByName_success() {
        // The command is: list n/destroyers
        ListCommand listCommand = new ListCommand(Optional.of(destroyerOnlySet), Optional.empty());

        StringBuilder expectedOutput = new StringBuilder();

        expectedOutput.append(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION))
                .append("\n");

        CommandResult expectedCommandResult = new CommandResult(expectedOutput.toString(), false, false);
        assertCommandSuccess(listCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listByMultipleTags_success() {
        // The command is: list t/testTag1 t/testTag2
        ListCommand listCommand = new ListCommand(Optional.empty(), Optional.of(testTagSet1));

        StringBuilder expectedOutput = new StringBuilder();

        expectedOutput.append(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION))
                .append("\n");

        CommandResult expectedCommandResult = new CommandResult(expectedOutput.toString(), false, false);
        assertCommandSuccess(listCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listBySingleTag_success() {
        // The command is: list t/testTag1
        ListCommand listCommand = new ListCommand(Optional.empty(), Optional.of(testTagSet2));
        List<String> expectedStringList = new ArrayList<>();

        expectedStringList.add(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION));
        expectedStringList.add(buildOutput(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION));

        assertTrue(isCommandResultEqual(expectedStringList,
                listCommand.execute(model, commandHistory).getFeedbackToUser()));
    }

    @Test
    public void execute_listByMultipleNames_success() {
        // The command is: list n/destroyer n/cruiser
        ListCommand listCommand = new ListCommand(Optional.of(cruiserAndDestroyerSet), Optional.empty());
        List<String> expectedStringList = new ArrayList<>();

        expectedStringList.add(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION));
        expectedStringList.add(buildOutput(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION));

        Collections.sort(expectedStringList);

        assertTrue(isCommandResultEqual(expectedStringList,
                listCommand.execute(model, commandHistory).getFeedbackToUser()));
    }

    @Test
    public void execute_listBySingleNameAndMultipleTags_success() {
        // The command is: list n/destroyer t/testTag1 t/testTag2
        setUp();
        ListCommand listCommand = new ListCommand(Optional.of(destroyerOnlySet), Optional.of(testTagSet1));

        StringBuilder expectedOutput = new StringBuilder();

        expectedOutput.append(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION))
                .append("\n");

        CommandResult expectedCommandResult = new CommandResult(expectedOutput.toString(), false, false);
        assertCommandSuccess(listCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listByMultipleNamesAndSingleTag_success() {
        // The command is: list n/destroyer n/cruiser t/tagSet1
        ListCommand listCommand = new ListCommand(Optional.of(cruiserAndDestroyerSet), Optional.of(testTagSet2));
        List<String> expectedStringList = new ArrayList<>();

        expectedStringList.add(buildOutput(battleshipTwo, VALID_COORDINATES_A2, VERTICAL_ORIENTATION));
        expectedStringList.add(buildOutput(battleshipOne, VALID_COORDINATES_A1, VERTICAL_ORIENTATION));

        Collections.sort(expectedStringList);

        assertTrue(isCommandResultEqual(expectedStringList,
                listCommand.execute(model, commandHistory).getFeedbackToUser()));
    }


    /**
     * Compares a command result string as a sorted list with an expected list.
     */
    private boolean isCommandResultEqual(List<String> expectedList, String commandResultString) {
        List<String> resultStringList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (String entry : commandResultString.split("\n")) {
            resultStringList.add(entry);
        }

        Collections.sort(resultStringList);
        Collections.sort(expectedList);

        return expectedList.equals(resultStringList);
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
