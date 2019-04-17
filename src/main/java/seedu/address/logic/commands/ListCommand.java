package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPARELS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.options.ListOption;
import seedu.address.model.Model;
import seedu.address.model.apparel.ClothingTypeValue;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.ColorValue;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all apparels";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List the apparels based on the option you supplied.\n"
            + "Example: list top\n"
            + "Outcome: all apparels that are of clothing type top.\n";

    public static final String MESSAGE_FAIL_COLOR_NO_VALUE = "Calling `list color` must include a "
            + "third parameter which is the value of the color\n"
            + "Format: `list color red`";

    public static final String MESSAGE_FAIL_COLOR_INVALID = "Invalid color given.";

    private final ListOption listOption;
    private final String optionValue;

    /**
     * @param listOption the list option
     */
    public ListCommand(ListOption listOption, String optionValue) {
        requireNonNull(listOption);

        this.listOption = listOption;
        this.optionValue = optionValue;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        if (ListOption.OPTIONS.equals(listOption)) {
            return new CommandResult(ListOption.allOptions());
        }

        if (ListOption.ALL.equals(listOption)) {
            model.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);
        } else if (ListOption.TOP.equals(listOption)) {
            sb.append(" by " + ListOption.TOP.toString().toLowerCase() + ".");
            model.updateFilteredApparelList(apparel -> apparel.getClothingType()
                    .getClothingTypeValue().equals(ClothingTypeValue.TOP));
        } else if (ListOption.BOTTOM.equals(listOption)) {
            sb.append(" by " + ListOption.BOTTOM.toString().toLowerCase() + ".");
            model.updateFilteredApparelList(apparel -> apparel.getClothingType()
                    .getClothingTypeValue().equals(ClothingTypeValue.BOTTOM));
        } else if (ListOption.BELT.equals(listOption)) {
            sb.append(" by " + ListOption.BELT.toString().toLowerCase() + ".");
            model.updateFilteredApparelList(apparel -> apparel.getClothingType()
                    .getClothingTypeValue().equals(ClothingTypeValue.BELT));
        } else if (ListOption.SHOES.equals(listOption)) {
            sb.append(" by " + ListOption.SHOES.toString().toLowerCase() + ".");
            model.updateFilteredApparelList(apparel -> apparel.getClothingType()
                    .getClothingTypeValue().equals(ClothingTypeValue.SHOES));
        } else if (ListOption.COLOR.equals(listOption)) {
            if (optionValue == null) {
                return new CommandResult(MESSAGE_FAIL_COLOR_NO_VALUE);
            } else if (!ColorValue.isValidColor(optionValue)) {
                return new CommandResult(MESSAGE_FAIL_COLOR_INVALID + "\n" + Color.allValidColors());
            }

            sb.append(" by " + ListOption.COLOR.toString().toLowerCase() + ".");
            model.updateFilteredApparelList(apparel -> apparel.getColor()
                    .getPrimary().equals(ColorValue.valueOf(optionValue.toUpperCase())));
        }

        return new CommandResult(sb.toString());


    }
}
