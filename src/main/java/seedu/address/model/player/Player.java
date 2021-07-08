package seedu.address.model.player;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.model.MapGrid;
import seedu.address.model.cell.Coordinates;

/**
 * This class implements a{@code Player} in the game.
 * The {@code Player} has a {@code Enemy} as its child.
 * @author lucydotc
 * @version 1.4
 * @since 2019-03-05
 */
public class Player {

    private static final int DEFAULT_NUM_AIRCRAFT_CARRIER = 1;
    private static final int DEFAULT_NUM_CRUISER = 2;
    private static final int DEFAULT_NUM_DESTROYER = 5;
    private static final String VALID_NAME_REGEX = "^[a-zA-Z0-9]{3,16}$";
    private static final String MESSAGE_CONSTRAINTS = "Name should contain only alphanumerical characters,"
            + "with no whitespaces.\n"
            + "and be of length 3 to 16 characters, inclusive.\n";

    private final String name;
    private final int fleetSize;
    private final Fleet fleet;
    private final MapGrid mapGrid;
    private Set<Coordinates> targetHistory = new HashSet<>();

    /**
     * This method is a constructor for the {@code Player} object
     * @param name The name of the {@code Player}
     * @param numDestroyer The number of {@code Destroyers} available for the {@code Player} to deploy
     * @param numCruiser The number of {@code Cruiser} available for the {@code Player} to deploy
     * @param numAircraftCarrier The number of {@code Aircraft Carrier} available for the {@code Player} to deploy
     */
    public Player(String name, int numDestroyer, int numCruiser, int numAircraftCarrier) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);

        this.name = name;
        this.fleetSize = numDestroyer + numCruiser + numAircraftCarrier;
        this.fleet = new Fleet(DEFAULT_NUM_DESTROYER, DEFAULT_NUM_CRUISER, DEFAULT_NUM_AIRCRAFT_CARRIER);
        this.mapGrid = new MapGrid();
    }

    /**
     * This method is the default constructor for the {@code Player}
     */
    public Player() {
        this("Player1",
                DEFAULT_NUM_DESTROYER,
                DEFAULT_NUM_CRUISER,
                DEFAULT_NUM_AIRCRAFT_CARRIER);
    }


    /**
     * This method attempts to add targeted coordinates to the {@code Player}'s targetHistory
     * It checks for duplicates, and returns False if adding failed (duplicate found)
     * Returns True if adding succeeded (coordinate added to targetHistory)
     * @return boolean
     */
    public boolean addToTargetHistory(Coordinates target) {
        return this.targetHistory.add(target);
    }

    /**
     * This a getter method that returns the name of the {@code Player}
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method resets the deployed fleet of the {@code Player}
     * and is called when resetting or starting a new game.
     */
    public void resetFleet(int mapSize) {
        fleet.resetFleet(mapSize);
    }

    /**
     * This method resets the targetHistory of the {@code Player}
     * and is called when resetting or starting a new game.
     */
    public void resetTargetHistory() {
        targetHistory = new HashSet<>();
    }

    /**
     * This a getter method that returns thesize of the {@code Player}'s fleet
     * @return int
     */
    public int getFleetSize() {
        return this.fleetSize;
    }

    /**
     * This a getter method that returns the {@code Fleet} of the {@code Player}
     * @return Fleet
     */
    public Fleet getFleet() {
        return this.fleet;
    }

    /**
     * This a getter method that returns the contents of the {@code Fleet} belonging to the {@code Player}
     * @return ArrayList
     */
    public ArrayList getFleetContents() {
        return this.fleet.getDeployedFleet();
    }

    /**
     * This a getter method that returns the {@code MapGrid} belonging to the {@code Player}
     * @return MapGrid
     */
    public MapGrid getMapGrid() {
        return this.mapGrid;
    }

    /**
     * This a getter method that returns the {@code targetHistory} belonging to the {@code Player}
     * @return Set
     */
    public Set getTargetHistory() {
        return this.targetHistory;
    }

    /**
     * This a method that checks if the supplied name to the {@Player} constructor is valid
     * @param name The supplied name for the {@code Player}
     * If valid, return true.
     * If invalid, return false.
     * @return boolean
     */
    public static boolean isValidName(String name) {
        return Pattern.matches(VALID_NAME_REGEX, name) && !name.equals("Enemy");
    }


    /**
     * This method overrides the default toString method
     * to allow for comparison of multiple {@code Player} objects
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(getFleet())
                .append(Arrays.toString(targetHistory.toArray()));
        return builder.toString();
    }
}
