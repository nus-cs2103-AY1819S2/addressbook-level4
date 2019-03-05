package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand>{

    @Override
    public AddAppointmentCommand parse(String userInput) throws ParseException {
        return new AddAppointmentCommand();
    }
}
