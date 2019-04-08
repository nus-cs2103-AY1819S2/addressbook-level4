package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.TransformationSet;
import seedu.address.model.image.Image;

public class AddPresetCommand extends Command{

    public static final String COMMAND_WORD = "savePresetCommand";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Create a command which saves a preset list of image editing commands used on your currently editing image."
        + "Example: " + COMMAND_WORD;

    private String presetName;

    /**
     * Creates a AddPreserCommand object.
     * @param presetName name given to the preset set by user
     */
    public AddPresetCommand(String presetName) {
        this.presetName = presetName;
    }


    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
        throws CommandException {
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        List<Command> presetList = currentEdit.getTempSubHistory();
        TransformationSet transformationSet = TransformationSet.getInstance();
        transformationSet.addTransformation(presetName, presetList);

        return new CommandResult(Messages.MESSAGE_ADDPRESET_SUCCESS);
    }

}
