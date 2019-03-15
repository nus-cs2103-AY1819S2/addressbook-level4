package seedu.address.model.battleship;

import java.util.HashSet;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CruiserBattleship extends Battleship {

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public CruiserBattleship() {
        super(new Name("cruiser"), 2, 2, new HashSet<>());
    }

}
