package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.player.Fleet;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public final Set<Tag> tagSet;

    public ListCommand(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Fleet fleet = model.getFleet();

        if (fleet.getDeployedFleet().size() <= 0) {
            return new CommandResult("No ships put down.");
        }

        StringBuilder builder = new StringBuilder();

        if (tagSet.isEmpty()) {
            for (int i = 0; i < fleet.getDeployedFleet().size(); i++) {
                builder.append(fleet.getDeployedFleet().get(i))
                        .append("\n");
            }

            return new CommandResult(builder.toString());
        }

        ArrayList<Fleet.FleetEntry> fleetList = model.getHumanPlayer().getFleet().getByTags(tagSet);

        for (Fleet.FleetEntry fleetEntry : fleetList) {
            builder.append(fleetEntry)
                    .append("\n");
        }

        return new CommandResult(builder.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand) // instanceof handles nulls
                && this.tagSet.equals(((ListCommand) other).tagSet);
    }
}
