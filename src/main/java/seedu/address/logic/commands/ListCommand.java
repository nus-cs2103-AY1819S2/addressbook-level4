package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import seedu.address.logic.CommandHistory;
import seedu.address.model.MapGrid;
import seedu.address.model.Model;
import seedu.address.model.battleship.Battleship;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Battleship> listOfBattleships = new ArrayList<Battleship>();

        MapGrid mapGrid = model.getMapGrid();
        int size = mapGrid.getMapSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mapGrid.getCell(i, j).hasBattleShip()) {
                    listOfBattleships.add(mapGrid.getCell(i, j).getBattleship().get());
                }
            }
        }

        String result = "";

        for (Battleship bs : listOfBattleships) {
            result += bs.toString();
        }
        return new CommandResult(result);
    }
}
