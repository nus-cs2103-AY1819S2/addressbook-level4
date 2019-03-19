package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.model.tag.ConditionTag;

/**
 * Interface that represents a Command object involving a Patient
 * TODO: add abstract methods involving Patient Command
 *
 * @author Rohan
 */
public interface PatientCommand {

    String COMMAND_OPTION = "2";

    String COMMAND_PARAMETERS = PREFIX_NAME + "NAME "
        + PREFIX_NRIC + "NRIC "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS ";

    String COMMAND_EXAMPLE = PREFIX_NAME + "Pay Shunt "
        + PREFIX_NRIC + "S1234567A"
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_CONDITION + ConditionTag.parseString("Cancer") + " "
        + ConditionTag.parseString("Stroke");

    String DUPLICATE_PATIENT = "This patient "
        + "already exists in the address book";
}
