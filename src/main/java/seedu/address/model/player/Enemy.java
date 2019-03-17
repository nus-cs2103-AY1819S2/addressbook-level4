package seedu.address.model.player;

import java.util.Random;

import seedu.address.model.cell.Coordinates;

/**
 * Represents an Enemy player in the game.
 * Enemy will always be a computerised player.
 */
public class Enemy extends Player {

    private static final Random randGen = new Random();

    /**
     * Constructor with custom fleetSize.
     */
    public Enemy(int fleetSize) {
        super("Enemy", 5, 2, 1);
    }

    /**
     * Default constructor with fleet size 5.
     */
    public Enemy() {
        super("Enemy", 5, 2, 1);
    }

    /**
     * Creates and returns valid Coordinate to be shot at.
     * Adds Coordinates into targetHistory
     */
    public Coordinates enemyShootAt() {

        int mapSize = this.getMapGrid().getMapSize();
        boolean nonDuplicateCoord = false;
        Coordinates target;

        //interim measure. bruteforcing til a non-duplicate is found
        do {
            target = generateCoordinates(mapSize);
            nonDuplicateCoord = addToTargetHistory(target);
        } while (nonDuplicateCoord == false);
        return target;
    }

    /**
     * generates valid coordinates for shooting
     */
    public Coordinates generateCoordinates(int mapSize) {

        String xCoords = "abcdefghij";
        int x = randGen.nextInt(mapSize);
        int y = randGen.nextInt(mapSize) + 1;
        String xy = xCoords.charAt(x) + Integer.toString(y);
        return new Coordinates(xy);

    }



}
