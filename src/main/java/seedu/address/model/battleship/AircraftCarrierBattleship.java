package seedu.address.model.battleship;

import java.util.HashSet;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class AircraftCarrierBattleship extends Battleship {

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public AircraftCarrierBattleship() {
        super(new Name("aircraft carrier"), 5, 5, new HashSet<>());
    }

}
