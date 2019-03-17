package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Statistics;

/**
 * Sets the consultation fee of the clinic.
 */
public class SetConsultationFeeCommand extends Command {

    public static final String COMMAND_WORD = "setconsultfee";
    public static final String COMMAND_ALIAS = "setfee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sets the consultation fee to the desired value.\n"
            + "Parameters: FEE\n"
            + "Example: " + COMMAND_WORD + " 30.00";

    public final BigDecimal fee;

    public SetConsultationFeeCommand(BigDecimal fee) {
        requireNonNull(fee);
        this.fee = fee;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.setConsultationFee(fee);
            StringBuilder sb = new StringBuilder();
            sb.append("Consultation fee has been successfully changed to $" + Statistics.currencyFormat(fee));
            sb.append("\n\n");
            return new CommandResult(sb.toString());
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetConsultationFeeCommand)) {
            return false;
        }
        SetConsultationFeeCommand sc = (SetConsultationFeeCommand) other;
        return this.fee.compareTo(sc.fee) == 0;
    }
}
