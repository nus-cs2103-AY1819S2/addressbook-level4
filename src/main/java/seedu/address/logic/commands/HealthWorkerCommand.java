package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;

import seedu.address.model.tag.Specialisation;

/**
 * Interface that represents a Command object involving a HealthWorker
 * TODO: add abstract methods involving HealthWorkerCommand
 * @author Lookaz
 */
public interface HealthWorkerCommand {

    String COMMAND_OPTION = "healthworker/h/1";

    String ADD_COMMAND_PARAMETERS = PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ORGANIZATION + "ORGANIZATION "
            + PREFIX_SKILLS + "SPECIALISATION...";

    String ADD_COMMAND_EXAMPLE = PREFIX_NAME + "Dog Terr "
            + PREFIX_NRIC + "S1234567A"
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ORGANIZATION + "NUH "
            + PREFIX_SKILLS + Specialisation.PHYSIOTHERAPY + " "
            + Specialisation.GENERAL_PRACTICE;

    String EDIT_COMMAND_PARAMETERS = "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_ORGANIZATION + "ORGANIZATION] "
            + "[" + PREFIX_SKILLS + "SPECIALISATION]...\n";

    String EDIT_COMMAND_EXAMPLE = PREFIX_NAME + "Pay Shun "
            + PREFIX_PHONE + "88884444\n";

    String DUPLICATE_HEALTH_WORKER = "This health worker "
            + "already exists in the address book";
}
