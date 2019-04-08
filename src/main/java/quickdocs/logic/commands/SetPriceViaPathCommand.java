package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Optional;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.medicine.Medicine;

/**
 * Set the price of a medicine via path
 */
public class SetPriceViaPathCommand extends SetPriceCommand {

    private String[] path;
    private BigDecimal price;

    public SetPriceViaPathCommand(String[] path, BigDecimal price) {
        this.path = path;
        this.price = price;
    }

    /**
     * Set the price of a medicine found through path
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory the commandHistory to record on
     * @return CommandResult showing the result
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {
            Optional<Medicine> medicine = model.findMedicine(path);
            if (!medicine.isPresent()) {
                throw new CommandException("Invalid path.");
            }
            model.setPrice(medicine.get(), price);
            return new CommandResult(String.format(MESSAGE_SUCCESS, medicine.get().name, price.toString()));
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }
}
