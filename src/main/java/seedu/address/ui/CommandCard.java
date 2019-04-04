/* @@author Carrein */

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.logic.commands.Command;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CommandCard extends UiPart<Region> {

    private static final String FXML = "CommandListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Text commandName;
    @FXML
    private Text arguments;

    public CommandCard(Command command) {
        super(FXML);
        commandName.setText("\uD83D\uDCD6 " + command.getCommandName());
        arguments.setText("Arguments: " + command.getArguments());
    }
}
