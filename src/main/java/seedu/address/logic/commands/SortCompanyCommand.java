package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Company;

import java.util.Comparator;

public class SortCompanyCommand extends Command{
    public final Comparator<Company> Compare;
    public final String Compare_parameter;
    public final String Sequence;

    public static final String COMMAND_WORD = "sortcpny";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort Company by specify parameters in sequence or reverse order\n"
            + "Example: sortcpny name seq(for sequence order)\n"
            + "Example: sortcpny name rev(for reverse order)";

    public static final String MESSAGE_SUCCESS = "Company have been sorted! ";


    public SortCompanyCommand(Comparator<Company> com_function, String Com_para, String Seq) {
        this.Compare = com_function;
        this.Compare_parameter = Com_para;
        this.Sequence = Seq;
    }

    @Override
    public  CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.sortCompany(this.Compare, this.Sequence);
        model.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);
        if(!this.Sequence.equals("seq") || this.Sequence.equals("rev")) {
            throw new CommandException("error");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCompanyCommand // instanceof handles nulls
                && Compare.equals(((SortCompanyCommand) other).Compare) // state check
                && Compare_parameter.equals(((SortCompanyCommand) other).Compare_parameter)
                && Sequence.equals(((SortCompanyCommand) other).Sequence));
    }
}
