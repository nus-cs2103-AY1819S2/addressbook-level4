package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CropCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            String fileName = parsed[4];
            System.out.println(xValue + " " + yValue + " " + width + " " + height + " " + fileName);
            return new CropCommand(xValue, yValue, width, height, fileName);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_CROP_INT_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.MESSAGE_CROP_INSUFFICIENT_INPUTS);
        }

    }
}
