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
public class SetPresetCommand extends Command {
    public static final String COMMAND_WORD = "setpreset";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Use a preset list of image editing commands on your image with the given preset name.\n"
        + "Example: " + COMMAND_WORD + "preset1\n";

    private String presetName;
    private boolean isNewCommand;
    private boolean hasWaterMarkCommand;

    /**
     * Creates a PresetCommand object.
     * @param presetName name given to the preset set by user
     */
    public SetPresetCommand(String presetName) {
        setCommandName(COMMAND_WORD);
        setArguments(presetName);
        this.presetName = presetName;
        this.isNewCommand = true;
        this.hasWaterMarkCommand = false;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Model model, CommandHistory history)
        throws CommandException {
        if (currentEdit.tempImageDoNotExist()) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        Image initialImage = currentEdit.getTempImage();
        TransformationSet transformationSet = TransformationSet.getInstance();
        boolean isPresent = transformationSet.isPresent(presetName);
        if (!isPresent) {
            throw new CommandException(Messages.MESSAGE_SETPRESET_FAIL_NOTFOUND);
        }
        List<Command> presetList = transformationSet.findTransformation(presetName);
        this.hasWaterMarkCommand = transformationSet.hasWaterMarkCommand(presetName);
        StringBuilder toPrint = new StringBuilder();
        for (Command command: presetList) {
            try {
                toPrint.append("[" + command.toString() + "]");
                command.execute(currentEdit, model, history);
            } catch (CommandException e) {
                List<Command> listBefore = initialImage.getSubHistory();
                currentEdit.replaceTempWithOriginal();
                currentEdit.getTempImage().setWaterMark(false);
                for (Command pastCommand: listBefore) {
                    pastCommand.execute(currentEdit, model, history);
                }
                String exceptionString = e.toString().substring(58);
                throw new CommandException("Error in [" + command.toString() + "]:\n" + exceptionString);
            }
        }

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        }

        return new CommandResult(Messages.MESSAGE_SETPRESET_SUCCESS + " " + toPrint.toString());
    }

    public boolean hasWaterMarkCommand() {
        return this.hasWaterMarkCommand;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SetPresetCommand // instanceof handles nulls
            && (this.presetName.equals(((SetPresetCommand) other).presetName)));
    }
}
/* @@author*/
