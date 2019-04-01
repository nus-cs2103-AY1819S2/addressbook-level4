package seedu.address.model.player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;

import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;



/**
 * Represents an Enemy player in the game.
 * Enemy will always be a computerised player.
 */
public class Enemy extends Player {

    private static final String xCoordinates = "abcdefghij";
    private static final Random randGen = new Random();
    private static final Random randGen2 = new Random();

    private static final ArrayList<String> allPossibleTargets = new ArrayList<String>(); //one-based
    private static final ArrayList<String> allParityTargets = new ArrayList<String>(); //one-based
    private static ArrayList<String> allPossiblePopulateCoords = new ArrayList<String>(); //zero-based, two ints
    private Stack<String> watchlist = new Stack<String>();
    private Status lastAttackStatus;
    private int mapSize = 0;


    private String lastCoordAttacked;

    /**
     * Default constructor with fleet size 8.
     */
    public Enemy() {
        super("EnemyPlayer", 5, 2, 1);
    }

    /**
     * Initialises and resets enemy's understanding of
     * its own and the player's map grids.
     * Used to inform the algorithms for populating enemy mapgrid and enemy shooting
     */
    public void prepEnemy() {
        mapSize = this.getMapGrid().getMapSize();
        fillAllPossibleTargets();
        fillallPossiblePopulateCoords();
        populateEnemyMap();
    }

    /**
     * Draws and returns valid Coordinate to be shot at.
     * Adds Coordinates into targetHistory
     * Called by battle manager
     */
    public Coordinates enemyShootAt() {
        Coordinates newTarget;

        if (watchlist.isEmpty()) {
            newTarget = drawPartityTarget();
        } else {
            newTarget = drawFromWatchList();
        }
        modeCleanup(newTarget.toString());

        this.addToTargetHistory(newTarget);
        return newTarget;

    }
    /**
     * draws a valid String coordinate for shooting
     * from list of valid coordinates with parity filter on
     * Creates and returns Coord with drawn String Coord
     */
    private Coordinates drawPartityTarget() {
        String targetCoord = allParityTargets.get(0);
        Coordinates target = new Coordinates(targetCoord);
        return target;
    }

    private Coordinates drawFromWatchList() {
        String targetCoord = watchlist.pop();
        Coordinates target = new Coordinates(targetCoord);
        return target;
    }

    /**
     * fills allPossibleTarget ArrayList with
     * all valid Strings of coordinates for shooting
     */
    private void fillAllPossibleTargets () {
        for (int xIndex = 0; xIndex < mapSize; xIndex++) {
            for (int yIndex = 0; yIndex < mapSize; yIndex++) {
                String xy = xCoordinates.charAt(xIndex) + Integer.toString(yIndex + 1);
                allPossibleTargets.add(xy);
                if (hasPartity(xy)) {
                    allParityTargets.add(xy);
                }
            }
        }
        java.util.Collections.shuffle(allPossibleTargets, randGen);
    }
    /**
     * fills allPossiblePopulateCoords ArrayList with
     * all valid Strings of coordinates for populating with ships
     */
    private void fillallPossiblePopulateCoords() {
        for (int xIndex = 0; xIndex < mapSize; xIndex++) {
            for (int yIndex = 0; yIndex < mapSize; yIndex++) {
                allPossiblePopulateCoords.add(Integer.toString(xIndex) + yIndex);
            }
        }
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
    }

    /**
     * randomly puts ships from enemy deployedFleet to enemy mapGrid
     * is part of enemy constructor
     */
    private void populateEnemyMap() {

        ArrayList deployedFleet = this.getFleetContents();
        int fleetSize = this.getFleetSize();

        int numAirCraftCarrier = this.getFleet().getNumAircraftCarrier();
        int numDestroyer = this.getFleet().getNumDestroyer();
        int numCruiser = this.getFleet().getNumCruiser();

        placeAirCraftCarrier();
        placeMultipleDestroyerAndCruiser(numDestroyer, "Destroyer", 3);
        placeMultipleDestroyerAndCruiser(numCruiser, "Cruiser", 2);
    }

    /**
     * pulls a head coord from allPossiblePopulateCoords
     * and justifies the coord before using it as a head for the aircraftCarrier
     * before placing the aircraftCarrier onto enemy map
     */
    private void placeAirCraftCarrier() {
        Orientation useOrientation = generateOrientation();
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen);
        String xyCoord = allPossiblePopulateCoords.get(0);
        int x = Character.getNumericValue(xyCoord.charAt(0));
        int y = Character.getNumericValue(xyCoord.charAt(1));

        if (useOrientation.isHorizontal()) { //rectify X coord of head, Y doesn't matter (all Y will work)
            y = justifyCoord(y, 5);
        } else {
            x = justifyCoord(x, 5);
        }
        Name currentBattleshipName = new Name("enemyAircraftCarrier");
        Battleship currentBattleship = new Battleship(currentBattleshipName);
        Coordinates currentBattleshipHead = new Coordinates(x, y);
        //should have no error, since it is the first ship placed, and all map sizes have a max of 1 aircraft carrier
        this.getMapGrid().putShip(currentBattleship, currentBattleshipHead, useOrientation);
        markAsOccupied(x, y, 5, useOrientation);
    }

    /**
     * places all destroyers and cruisers onto enemy map
     * and marks those occupied cells in allPossiblePopulateCoords
     */
    private void placeMultipleDestroyerAndCruiser(int numShips, String shipType, int shipSize) {
        Orientation useOrientation;
        String xyCoord;
        int x;
        int y;
        boolean suceededPlacingShip = false;
        ArrayList<Battleship> preppedShips = generateBattleships(numShips, shipType);

        while (!preppedShips.isEmpty()) {
            try {
                useOrientation = generateOrientation();
                java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
                xyCoord = allPossiblePopulateCoords.get(0);
                x = Character.getNumericValue(xyCoord.charAt(0));
                y = Character.getNumericValue(xyCoord.charAt(1));
                this.getMapGrid().putShip(preppedShips.get(0), new Coordinates(x, y), useOrientation);
                preppedShips.remove(0);
                markAsOccupied(x, y, shipSize, useOrientation);
            } catch (ArrayIndexOutOfBoundsException e) {
                //TODO log the error later from putship
            }
        }

    }



    /**
     * creates list of a certain battleship type
     * to be put on map
     */
    private ArrayList<Battleship> generateBattleships (int numShips, String battleshipType) {
        ArrayList<Battleship> preppedShips = new ArrayList<>();
        for (int i = 1; i <= numShips; i++) {
            Name currentBattleshipName = new Name("enemy" + battleshipType + i);
            Battleship currentBattleship = new Battleship(currentBattleshipName);
            preppedShips.add(currentBattleship);
        }
        return preppedShips;
    }
    /**
     * removes all the occupied Coords from allPossiblePopulateCoords
     * when a new ship is placed
     */
    private void markAsOccupied(int x, int y, int shipSize, Orientation useOrientation) {

        //increase x
        if (useOrientation.isHorizontal()) {
            for (int i = 0; i < shipSize; i++) {
                allPossiblePopulateCoords.remove(Integer.toString(x + i) + y);
            }
        } else { //increase y
            for (int i = 0; i < shipSize; i++) {
                allPossiblePopulateCoords.remove(Integer.toString(x) + (y + i));
            }
        }
    }

    /**
     * justifies the head coord given to ensure
     * ship to be placed can fit the map boundaries
     */
    private int justifyCoord(int coordToJustify, int shipSize) {
        int lowerHalfceiling = (mapSize / 2) - 1;
        switch (mapSize) {
        case 6:
            if (coordToJustify <= lowerHalfceiling) {
                coordToJustify = 0;
            } else {
                coordToJustify = 1;
            }
            break;
        case 10:
            lowerHalfceiling += 1;
            if (coordToJustify > lowerHalfceiling) {
                coordToJustify -= 4;
            }
            break;
        default:
            coordToJustify %= lowerHalfceiling + 1;
        }
        return coordToJustify;
    }


    /**
     * randomly generates either a horizontal or vertical orientation
     */
    private Orientation generateOrientation() {
        int seed = randGen2.nextInt();
        int decision = seed % 2;
        if (decision == 0) {
            return new Orientation("horizontal");
        } else {
            return new Orientation("vertical");
        }
    }

    /**
     * receives status of an attacked cell from Battle manager
     */
    public void receiveStatus(Status latestStatusInfo) {
        //lastCoordAttacked in a1 format
        lastAttackStatus = latestStatusInfo;
        updateWatchlist(lastCoordAttacked);
    }


    /**
     * Add coords of the cardinal positions to the last attacked cell
     * to the watchlist
     */
    private void updateWatchlist(String lastxyAttacked) {
        if (lastAttackStatus == Status.SHIPHIT) {

            Coordinates xyAsCoords = new Coordinates(lastxyAttacked);
            int x = xyAsCoords.convertAlphabetToNumber(lastxyAttacked); //zero based
            int y = (int) lastxyAttacked.charAt(1) - 1; //one based to zero based

            //ADD CARDINAL DIRECTIONS TO WATCHLIST.
            //WATCHLIST COORDS IN FORM a1

            if (y - 1 >= 0) {
                //add cardinal north to watchlist
                watchlist.add(xCoordinates.charAt(x) + Integer.toString(y - 1 + 1));
            }
            if (y + 1 < mapSize) {
                //add cardinal south to watchlist
                watchlist.add(xCoordinates.charAt(x) + Integer.toString(y + 1 + 1));
            }
            if (x - 1 >= 0) {
                //add cardinal west to watchlist
                watchlist.add(xCoordinates.charAt(x - 1) + Integer.toString(y + 1));
            }
            if (x + 1 < mapSize) {
                //add cardinal east to watchlist
                watchlist.add(xCoordinates.charAt(x + 1) + Integer.toString(y + 1));

            }

        }
    }

    /**
     * Remove the last used coord
     * from allParityTargets and allPossibleTargets
     */
    private void modeCleanup(String usedCoord) {
        //usedCoord in a1 format
        allParityTargets.remove(usedCoord);
        allPossibleTargets.remove(usedCoord);
        lastCoordAttacked = usedCoord;
    }

    /**
     * checks that coord is made up of one odd and one even x-y coordinate pair
     * and returns true if so
     */
    private boolean hasPartity (String xy) {

        int parity = Character.getNumericValue(xy.charAt(0)) % 2
                + Character.getNumericValue(xy.charAt(1)) % 2;
        return parity == 1;
    }
}
