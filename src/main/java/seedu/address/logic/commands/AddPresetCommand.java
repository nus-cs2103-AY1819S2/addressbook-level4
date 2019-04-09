/* @@author thamsimun */
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.TransformationSet;
import seedu.address.model.image.Image;

/**
 * This command allows users to use a preset of commands.
 */
public class AddPresetCommand extends Command {
    public static final String COMMAND_WORD = "addPreset";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Use a preset list of image editing commands on your image with the given preset name.\n"
        + "Example: " + COMMAND_WORD + "preset1\n";

    private String presetName;
    private boolean isNewCommand;

    /**
     * Creates a PresetCommand object.
     * @param presetName name given to the preset set by user
     */
    public AddPresetCommand(String presetName) {
        this.presetName = presetName;
        this.isNewCommand = true;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
        throws CommandException {
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        TransformationSet transformationSet = TransformationSet.getInstance();
        boolean isPresent = transformationSet.isPresent(presetName);
        if (!isPresent) {
            throw new CommandException(Messages.MESSAGE_PRESET_FAIL_NOTFOUND);
        }
        List<Command> presetList = transformationSet.findTransformation(presetName);
        System.out.print(presetList);
        for (Command command: presetList) {
            try {
                command.execute(currentEdit, model, history);
            } catch (CommandException exception) {
                throw new CommandException("Error in [" + command.toString() + "\n" + exception.toString());
            }
        }

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }

        return new CommandResult(Messages.MESSAGE_PRESET_SUCCESS);
    }
}
/* @@author*/
