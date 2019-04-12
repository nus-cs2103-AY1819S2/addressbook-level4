package seedu.address.model.battleship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Battleship {
    public static final String MESSAGE_CONSTRAINTS = "Name must be "
            + BattleshipType.AIRCRAFT_CARRIER.toString() + ", "
            + BattleshipType.DESTROYER.toString() + " or "
            + BattleshipType.CRUISER.toString() + ".";

    // Identity fields
    private static int counter = 0;
    protected final int id;
    protected final Name name;

    // Data fields
    protected final Set<Tag> tags = new HashSet<>();
    protected final int length;
    private int life;

    // Logger
    private final Logger logger = LogsCenter.getLogger(Battleship.class);

    /**
     * Every field must be present and not null.
     */
    public Battleship(Name name, int length, int life, Set<Tag> tags) {
        logger.info("BATTLESHIP INITIALISED.");
        this.id = counter++;
        this.name = name;
        this.length = length;
        this.life = life;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Battleship without arguments.
     */
    public Battleship() {
        this(BattleshipType.DEFAULT.getName(), BattleshipType.DEFAULT.length,
                BattleshipType.DEFAULT.length, new HashSet<Tag>());
    }

    /**
     * Constructor for Battleship with only name.
     */
    public Battleship(Name name) {
        this(name, BattleshipType.DEFAULT.length,
                BattleshipType.DEFAULT.length, new HashSet<Tag>());
    }

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public Battleship(Name name, Set<Tag> tags) {
        this(name, BattleshipType.DEFAULT.length,
                BattleshipType.DEFAULT.length, tags);
    }

    /**
     * Constructor Battleship with only name, length and size.
     */
    public Battleship(Name name, int length, int life) {
        this(name, length, life, new HashSet<>());
    }

    /**
     * Getter method for name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Getter method for id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter method for length.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Getter method for life.
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Reduces the life of the battleship by one unit.
     */
    public void reduceLife() {
        this.life--;
    }

    /**
     * Checks if life of the battleship is equal to zero.
     * That is, if it is destroyed.
     */
    public boolean isDestroyed() {
        return this.life == 0;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both battleships of the same name have the same name.
     * This defines a weaker notion of equality between two battleships.
     */
    public boolean isSameBattleship(Battleship otherBattleship) {
        if (otherBattleship == this) {
            return true;
        }

        return otherBattleship != null
                && otherBattleship.getName().equals(getName())
                && otherBattleship.getId() == getId();
    }

    /**
     * Returns true if both battleships have the same identity fields.
     * This defines a stronger notion of equality between two battleships.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Battleship)) {
            return false;
        }

        Battleship otherBattleship = (Battleship) other;
        return otherBattleship.getName().equals(getName());
    }

    /**
     * Return hashcode of object.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, length, life, tags);
    }

    /**
     * Return name of battleship.
     */
    @Override
    public String toString() {
        return this.getName().toString();
    }

    /**
     * Enumeration of battleships for internal use.
     */
    public enum BattleshipType {
        /**
         * The values for AIRCRAFT_CARRIER, DESTROYER AND CRUISER are fixed.
         * The DEFAULT value is a placeholder for user-created battleships (coming in v2.0).
         */
        AIRCRAFT_CARRIER("aircraft carrier", 5),
        DESTROYER("destroyer", 3),
        CRUISER("cruiser", 2),
        DEFAULT("default", 2);

        private final String name;
        private final int length;

        /**
         * Constructor.
         */
        BattleshipType(String name, int length) {
            this.name = name;
            this.length = length;
        }

        /**
         * Returns name as a Name object.
         */
        public Name getName() {
            return new Name(this.name);
        }

        /**
         * Getter method for length.
         */
        public int getLength() {
            return this.length;
        }

        /**
         * Returns name as String.
         */
        @Override
        public String toString() {
            return this.name;
        }
    }

}
