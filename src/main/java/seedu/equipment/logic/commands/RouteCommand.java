package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Equipment;

/**
 * Selects an equipment identified using it's displayed index from the equipment manager.
 */
public class RouteCommand extends Command {

    public static final String COMMAND_WORD = "route";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Produce a optimized route from current location to the displayed equipment list.\n"
            + "Parameters: CURRENT ADDRESS (must be a valid address with postal code and country name)\n"
            + "Example: " + COMMAND_WORD + " School of Computing, NUS, Singapore 117417";

    public static final String MESSAGE_ROUTE_EQUIPMENT_SUCCESS = "Routes displayed on map.";

    public static final String MESSAGE_NO_EQUIPMENTS_TO_ROUTE = "At least 1 equipment in the equipment panel "
            + " is required to use route command.";

    public static final String MESSAGE_TOO_MANY_EQUIPMENTS_TO_ROUTE = "At most 15 equipment in the equipment panel "
            + " to use route command.";

    private final Address startendAddress;

    public RouteCommand(Address startendAddress) {
        this.startendAddress = startendAddress;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Equipment> filteredEquipmentList = model.getFilteredPersonList();

        if (filteredEquipmentList.size() < 1) {
            throw new CommandException(MESSAGE_NO_EQUIPMENTS_TO_ROUTE);
        }

        if (filteredEquipmentList.size() > 15) {
            throw new CommandException(MESSAGE_TOO_MANY_EQUIPMENTS_TO_ROUTE);
        }

        model.unsetSelectedEquipment();
        return new CommandResult(MESSAGE_ROUTE_EQUIPMENT_SUCCESS, false, false, false, true, startendAddress);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RouteCommand // instanceof handles nulls
                && startendAddress.equals(((RouteCommand) other).startendAddress)); // state check
    }
}
