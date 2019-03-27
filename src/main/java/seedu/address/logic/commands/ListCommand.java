package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.battleship.Name;
import seedu.address.model.player.Fleet;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private Optional<Set<Tag>> optionalTagSet;
    private Optional<Name> optionalName;

    public ListCommand(Optional<Set<Tag>> optionalTagSet, Optional<Name> optionalName) {
        this.optionalTagSet = optionalTagSet;
        this.optionalName = optionalName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Fleet fleet = model.getFleet();

        if (fleet.getDeployedFleet().size() <= 0) {
            return new CommandResult("No ships put down.");
        }

        StringBuilder builder = new StringBuilder();

        Set<Fleet.FleetEntry> fleetResult = new HashSet<>();

        if (!optionalTagSet.isPresent() && !optionalName.isPresent()) {
            // list all battleships
            for (int i = 0; i < fleet.getDeployedFleet().size(); i++) {
                builder.append(fleet.getDeployedFleet().get(i))
                        .append("\n");
            }
            return new CommandResult(builder.toString());
        } else {
            List<Fleet.FleetEntry> fleetList = new ArrayList<>();
            if (optionalName.isPresent() && !optionalTagSet.isPresent()) {
                // name
                fleetList = model.getHumanPlayer().getFleet().getByName(optionalName.get());
            } else if (optionalTagSet.isPresent() && !optionalName.isPresent()) {
                // tags only
                fleetList = model.getHumanPlayer().getFleet().getByTags(optionalTagSet.get());
            } else if (optionalName.isPresent() && optionalTagSet.isPresent()) {
                List<Fleet.FleetEntry> tempFleet = model.getHumanPlayer().getFleet().getByName(optionalName.get());
                fleetList = tempFleet.stream()
                        .filter(entry -> entry.getBattleship().getTags().containsAll(optionalTagSet.get()))
                        .collect(Collectors.toList());
            }

            fleetResult.addAll(fleetList);
        }

        for (Fleet.FleetEntry fleetEntry : fleetResult) {
            builder.append(fleetEntry)
                    .append("\n");
        }
        return new CommandResult(builder.toString());
    }

    public Optional<Name> getOptionalName() {
        return optionalName;
    }

    public Optional<Set<Tag>> getOptionalTagSet() {
        return optionalTagSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand) // instanceof handles nulls
                && this.getOptionalName().equals(((ListCommand) other).getOptionalName())
                && this.getOptionalTagSet().equals(((ListCommand) other).getOptionalTagSet());
    }
}
