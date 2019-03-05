package seedu.address.logic.parser;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddDoctorCommandParser implements Parser<AddDoctorCommand> {

    public AddDoctorCommand parse(String arguments) throws ParseException {
        return new AddDoctorCommand();
    }

}
