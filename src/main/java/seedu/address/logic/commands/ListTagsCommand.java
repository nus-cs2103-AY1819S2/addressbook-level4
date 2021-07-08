package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.Set;

import seedu.address.battle.state.BattleState;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Lists all the tags that have been used by ships deployed in the game.
 */
public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "listTags";

    public static final String MESSAGE_SUCCESS = "List all tags: ";
    public static final String TAGS_NOT_FOUND = "no tags found";
    public static final String TAGS_FOUND = " tags found\n";

    private Set<Tag> tagSet;

    public ListTagsCommand() {
        setPermissibleStates(EnumSet.of(
            BattleState.PLAYER_PUT_SHIP,
            BattleState.ENEMY_PUT_SHIP,
            BattleState.PLAYER_ATTACK,
            BattleState.ENEMY_ATTACK));
    }

    /**
     * Lists all the tags that have been used by ships deployed in the game.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult} which contains list of tags.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        StringBuilder stringBuilder = new StringBuilder();
        tagSet = model.getHumanPlayer().getFleet().getAllTags();

        if (tagSet.isEmpty()) {
            stringBuilder.append(TAGS_NOT_FOUND);
        } else {
            int tagSetSize = tagSet.size();
            int counter = 0;
            stringBuilder.append(tagSetSize).append(TAGS_FOUND);

            for (Tag tag : tagSet) {
                counter++;
                stringBuilder.append(tag.getTagName());
                if (counter < tagSetSize) {
                    stringBuilder.append(", ");
                }

            }
        }

        return new CommandResult(MESSAGE_SUCCESS + stringBuilder.toString());
    }

    /**
     * Getter method for {@code this.tagSet}.
     *
     * @return {@code this.tagSet}.
     */
    public Set<Tag> getTagSet() {
        return tagSet;
    }

    /**
     * Checks equality of two ListTagsCommand objects by comparing both {@code tagSet}s.
     * If the other object is not a {@code ListTagsCommand} object then they are not
     * equal.
     *
     * @param other any object.
     * @return boolean of whether the objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListTagsCommand)) {
            return false;
        }

        ListTagsCommand ltc = (ListTagsCommand) other;
        return tagSet.equals(ltc.getTagSet());
    }
}
