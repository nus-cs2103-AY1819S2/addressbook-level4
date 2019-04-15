package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.Collections;
import java.util.Comparator;
import seedu.address.logic.CommandHistory;
import seedu.address.model.person.Person;

public class SortPersonCommand extends Command {

    public final Comparator<Person> Compare;
    public final String Compare_parameter;
    public final String Sequence;

    public static final String COMMAND_WORD = "sortper";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort People by specify parameters in sequence or reverse order\n"
                                                            + "Example: sortper salary seq(for sequence order)\n"
                                                            + "Example: sortper salary rev(for reverse order)";

    public static final String MESSAGE_SUCCESS = "People have been sorted! ";


    public SortPersonCommand(Comparator<Person> com_function, String Com_para, String Seq) {
        this.Compare = com_function;
        this.Compare_parameter = Com_para;
        this.Sequence = Seq;
    }

    @Override
    public  CommandResult execute(Model model,CommandHistory history) throws CommandException {
        model.sortPerson(this.Compare, this.Sequence);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if(!this.Sequence.equals("seq") || this.Sequence.equals("rev")) {
            throw new CommandException("error");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortPersonCommand // instanceof handles nulls
                && Compare.equals(((SortPersonCommand) other).Compare) // state check
                && Compare_parameter.equals(((SortPersonCommand) other).Compare_parameter)
                && Sequence.equals(((SortPersonCommand) other).Sequence));
    }

}
