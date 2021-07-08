package seedu.address.model.battleship;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a cruiser in the game.
 */
public class CruiserBattleship extends Battleship {
    /**
     * Constructor for cruiser ship. The default length of the destroyer is 2.
     *
     * @param tagSet set of {@code Tag}s that the ship has.
     */
    public CruiserBattleship(Set<Tag> tagSet) {
        super(BattleshipType.CRUISER.getName(),
                BattleshipType.CRUISER.getLength(),
                BattleshipType.CRUISER.getLength(),
                tagSet);
    }

}
