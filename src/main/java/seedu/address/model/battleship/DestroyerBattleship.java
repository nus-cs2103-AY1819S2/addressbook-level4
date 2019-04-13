package seedu.address.model.battleship;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a destroyer in the game.
 */
public class DestroyerBattleship extends Battleship {
    /**
     * Constructor for destroyer ship. The default length of the destroyer is 3.
     *
     * @param tagSet set of {@code Tag}s that the ship has.
     */
    public DestroyerBattleship(Set<Tag> tagSet) {
        super(BattleshipType.DESTROYER.getName(),
                BattleshipType.DESTROYER.getLength(),
                BattleshipType.DESTROYER.getLength(),
                tagSet);
    }

}
