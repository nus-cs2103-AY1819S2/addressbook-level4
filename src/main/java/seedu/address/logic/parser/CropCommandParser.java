/* @@author kayheen */
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CropCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the crop command.
 */
public class CropCommandParser implements Parser<CropCommand> {
    /**
     * Parses the Crop Command.
     * @param args
     * @return a CropCommand object
     * @throws ParseException
     */
    public CropCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] parsed = args.split(" ");
            int xValue = Integer.parseInt(parsed[0]);
            int yValue = Integer.parseInt(parsed[1]);
            int width = Integer.parseInt(parsed[2]);
            int height = Integer.parseInt(parsed[3]);
            System.out.println(xValue + " " + yValue + " " + width + " " + height);
            return new CropCommand(xValue, yValue, width, height);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_CROP_INT_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(CropCommand.MESSAGE_USAGE);
        }

    }
}
