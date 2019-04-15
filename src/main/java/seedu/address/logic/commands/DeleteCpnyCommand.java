package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Company;

/**
 * Deletes a company identified using it's displayed index from the address book.
 */
public class DeleteCpnyCommand extends Command {

    public static final String COMMAND_WORD = "deletecpny";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Deleted Company: %1$s";

    private final Index targetIndexCpny;

    public DeleteCpnyCommand(Index targetIndex) {
        this.targetIndexCpny = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndexCpny.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToDelete = lastShownList.get(targetIndexCpny.getZeroBased());
        model.deleteCompany(companyToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_COMPANY_SUCCESS, companyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndexCpny.equals(((DeleteCpnyCommand) other).targetIndexCpny)); // state check
    }
}
