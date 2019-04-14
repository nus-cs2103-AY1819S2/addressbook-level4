package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.battle.state.BattleState;
import seedu.address.model.Model;
import seedu.address.model.battleship.Name;
import seedu.address.model.player.Fleet;
import seedu.address.model.tag.Tag;

/**
 * Lists ships of certain types, by certain tags, or both.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private Optional<Set<Tag>> optionalTagSet;
    private Optional<Set<Name>> optionalNameSet;

    public ListCommand(Optional<Set<Name>> optionalNameSet, Optional<Set<Tag>> optionalTagSet) {
        this.optionalTagSet = optionalTagSet;
        this.optionalNameSet = optionalNameSet;
        setPermissibleStates(EnumSet.of(
                BattleState.PLAYER_PUT_SHIP,
                BattleState.ENEMY_PUT_SHIP,
                BattleState.PLAYER_ATTACK,
                BattleState.ENEMY_ATTACK));
    }

    /**
     * Lists ships according to the prefixes specified by the user, which have
     * been parsed by {@code ListCommandParser}. There are four ways in which the
     * ships can be listed.
     *
     * 1. List all ships when no prefixes are provided.
     * 2. List ships with certain tags when tags are provided.
     * 3. List certain ships when names are provided.
     * 4. List certain ships with certain tags when names and tags are provided.
     *
     * The behaviour for (1) is such that there is no redundant checking against
     * the fleet for an empty set of names or tags. The fleet is simply returned in
     * such a case.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult} that contains list of ships.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        Fleet fleet = model.getHumanPlayer().getFleet();

        if (fleet.getDeployedFleet().size() <= 0) {
            return new CommandResult("No ships put down.");
        }

        StringBuilder builder = new StringBuilder();
        Set<Fleet.FleetEntry> fleetResult = new HashSet<>();

        if (!optionalTagSet.isPresent() && !optionalNameSet.isPresent()) {
            // list all battleships
            for (int i = 0; i < fleet.getDeployedFleet().size(); i++) {
                builder.append(fleet.getDeployedFleet().get(i)).append("\n");
            }
            return new CommandResult(builder.toString());
        } else if (optionalNameSet.isPresent() && optionalTagSet.isPresent()) {
            fleetResult.addAll(fleet.getDeployedFleet().stream()
                    .filter(fleetEntry -> optionalNameSet.get().contains(fleetEntry.getBattleship().getName()))
                    .filter(fleetEntry -> fleetEntry.getBattleship().getTags().containsAll(optionalTagSet.get()))
                    .collect(Collectors.toList()));
        } else if (optionalNameSet.isPresent()) {
            fleetResult.addAll(fleet.getByNames(optionalNameSet.get()));
        } else {
            fleetResult.addAll(fleet.getByTags(optionalTagSet.get()));
        }

        for (Fleet.FleetEntry fleetEntry : fleetResult) {
            builder.append(fleetEntry).append("\n");
        }

        if (fleetResult.isEmpty()) {
            return new CommandResult("There are no results.");
        }

        return new CommandResult(builder.toString());
    }

    /**
     * Getter method for {@code optionalNameSet}.
     *
     * @return {@code this.optionalNameSet}.
     */
    public Optional<Set<Name>> getOptionalNameSet() {
        return optionalNameSet;
    }

    /**
     * Getter method for {@code optionalTagSet}.
     *
     * @return {@code this.optionalTagSet}.
     */
    public Optional<Set<Tag>> getOptionalTagSet() {
        return optionalTagSet;
    }

    /**
     * Checks equality of two ListCommand objects by comparing the respective
     * {@code optionalNameSet} and {@code optionalTagSet}.
     *
     * @param other any object.
     * @return boolean of whether the objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand) // instanceof handles nulls
                && this.getOptionalNameSet().equals(((ListCommand) other).getOptionalNameSet())
                && this.getOptionalTagSet().equals(((ListCommand) other).getOptionalTagSet());
    }
}
