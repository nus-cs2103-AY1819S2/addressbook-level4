package seedu.address.model.battleship;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an aircraft carrier in the game.
 */
public class AircraftCarrierBattleship extends Battleship {
    /**
     * Constructor for destroyer ship. The default length of the destroyer is 5.
     *
     * @param tagSet set of {@code Tag}s that the ship has.
     */
    public AircraftCarrierBattleship(Set<Tag> tagSet) {
        super(BattleshipType.AIRCRAFT_CARRIER.getName(),
                BattleshipType.AIRCRAFT_CARRIER.getLength(),
                BattleshipType.AIRCRAFT_CARRIER.getLength(),
                tagSet);
    }

}
