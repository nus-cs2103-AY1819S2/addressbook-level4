package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Date;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;


/**
 * Lists all equipment in the equipment manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort by specified field\n"
            + "Parameters: sort [FIELD]... \n"
            + "Example 1: " + COMMAND_WORD + " name\n"
            + "Example 2: " + COMMAND_WORD + " address\n"
            + "Example 3: " + COMMAND_WORD + " email\n"
            + "Example 4: " + COMMAND_WORD + " phone\n";

    public static final String MESSAGE_SUCCESS = "Sorted by specified field";
    public static final String DEFAULT_SORT_PARAMETER = "equipment";
    public static final String ADDRESS_SORT_PARAMETER = "address";
    public static final String EMAIL_SORT_PARAMETER = "email";
    public static final String PHONE_SORT_PARAMETER = "phone";
    private final String sortParameter;

    public SortCommand(String args) {
        requireNonNull(args);
        sortParameter = args;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ObservableList<Equipment> originalEquipmentList = model.getFilteredPersonList();
        int size = originalEquipmentList.size();
        Comparator<Equipment> equipmentComparator;
        String trimmedSortParameterLowerCase = sortParameter.toLowerCase().trim();
        String commandResult;

        switch(trimmedSortParameterLowerCase) {
        case ADDRESS_SORT_PARAMETER:
            equipmentComparator = (firstEquipment, secondEquipment) -> {
                Address firstAddress = firstEquipment.getAddress();
                Address secondAddress = secondEquipment.getAddress();
                return firstAddress.compareTo(secondAddress);
            };
            commandResult = String.format(MESSAGE_SUCCESS, size, DEFAULT_SORT_PARAMETER);
            break;
        case PHONE_SORT_PARAMETER:
            equipmentComparator = (firstEquipment, secondEquipment) -> {
                Phone firstPhone = firstEquipment.getPhone();
                Phone secondPhone = secondEquipment.getPhone();
                return firstPhone.compareTo(secondPhone);
            };
            commandResult = String.format(MESSAGE_SUCCESS, size, DEFAULT_SORT_PARAMETER);
            break;
        case EMAIL_SORT_PARAMETER:
            equipmentComparator = (firstEquipment, secondEquipment) -> {
                Date firstDate = firstEquipment.getDate();
                Date secondDate = secondEquipment.getDate();
                return firstDate.compareTo(secondDate);
            };
            commandResult = String.format(MESSAGE_SUCCESS, size, DEFAULT_SORT_PARAMETER);
            break;
        default:
            equipmentComparator = (n1, n2) -> {
                Name firstName = n1.getName();
                Name secondName = n2.getName();
                return firstName.compareTo(secondName);
            };
            commandResult = String.format(MESSAGE_SUCCESS, size, DEFAULT_SORT_PARAMETER);
        }
        List<Equipment> sortedEquipmentList = new ArrayList<>(originalEquipmentList);
        sortedEquipmentList.sort(equipmentComparator);
        model.resetData(model.getEquipmentManager());
        for (Equipment newEquipment : sortedEquipmentList) {
            model.addEquipment(newEquipment);
        }
        model.commitEquipmentManager();
        return new CommandResult(commandResult);
    }
}
