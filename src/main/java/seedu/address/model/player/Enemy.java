package seedu.address.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import seedu.address.model.cell.Coordinates;

/**
 * Represents an Enemy player in the game.
 * Enemy will always be a computerised player.
 */
public class Enemy extends Player {

    private static final String xCoordinates = "abcdefghij";
    private static final Random randGen = new Random();

    private static final ArrayList allPossibleTargets = new ArrayList<String>();
    private final HashMap attackStatusHistory = new HashMap();

    /**
     * Default constructor with fleet size 5.
     */
    public Enemy() {
        super("EnemyPlayer", 5, 2, 1);
        fillAllPossibleTargets(this.getMapGrid().getMapSize());
    }

    /**
     * Draws and returns valid Coordinate to be shot at.
     * Adds Coordinates into targetHistory
     */
    public Coordinates enemyShootAt() {
        Coordinates newTarget = drawPossibleTarget();
        this.addToTargetHistory(newTarget);
        return newTarget;
    }

    /**
     * draws a valid String coordinate for shooting
     * from list of valid coordinates
     * removes this drawn String coordinate from the list AllPossibleTarget
     * Creates and returns Coord with drawn String Coord
     */
    private Coordinates drawPossibleTarget() {

        String targetCoord = (String) allPossibleTargets.get(0);
        allPossibleTargets.remove(0);
        Coordinates target = new Coordinates(targetCoord);
        return target;
    }

    /**
     * fills allPossibleTarget ArrayList with
     * all valid Strings of coordinates for shooting
     */
    private void fillAllPossibleTargets (int mapSize) {
        for (int xIndex = 0; xIndex < mapSize; xIndex++) {
            for (int yIndex = 0; yIndex < mapSize; yIndex++) {
                String xy = xCoordinates.charAt(xIndex) + Integer.toString(yIndex + 1);
                allPossibleTargets.add(xy);
            }
        }
        java.util.Collections.shuffle(allPossibleTargets, randGen);
    }

}
