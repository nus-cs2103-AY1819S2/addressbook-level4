package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.player.Fleet;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        // model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        Fleet fleet = model.getFleet();

        if (fleet.getDeployedFleet().size() <= 0) {
            return new CommandResult("No ships put down.");
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < fleet.getDeployedFleet().size(); i++) {
            builder.append(fleet.getDeployedFleet().get(i))
                    .append("\n");
        }

        return new CommandResult(builder.toString());
    }
}
