package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Optional;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.medicine.Medicine;

/**
 * Set the price of a medicine by its name
 */
public class SetPriceWoPathCommand extends SetPriceCommand {

    private String medicineName;
    private BigDecimal price;

    public SetPriceWoPathCommand(String name, BigDecimal price) {
        medicineName = name;
        this.price = price;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {
            Optional<Medicine> medicine = model.findMedicine(medicineName);
            if (!medicine.isPresent()) {
                throw new CommandException("No medicine with such name found.");
            }
            model.setPrice(medicine.get(), price);
            return new CommandResult(String.format(MESSAGE_SUCCESS, medicineName, price.toString()));
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }
}
