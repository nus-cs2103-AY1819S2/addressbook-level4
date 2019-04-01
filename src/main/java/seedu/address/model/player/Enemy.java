package seedu.address.model.player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
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

    private static final Random randGen = new Random();
    private static final Random randGen2 = new Random();
    private static final Logger logger = LogsCenter.getLogger(Enemy.class);


    private static ArrayList<Coordinates> allPossibleTargets = new ArrayList<>(); //one-based
    private static ArrayList<Coordinates> allParityTargets = new ArrayList<>(); //one-based
    private static ArrayList<Coordinates> allPossiblePopulateCoords = new ArrayList<>(); //zero-based, two ints
    private Stack<Coordinates> watchlist = new Stack<>(); //one based
    private Status lastAttackStatus;
    private int mapSize = 0;
    private Coordinates lastCoordAttacked;

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
        fillWithAllCoords();
        populateEnemyMap();
    }
    /**************************************************
     * fills allPossibleTargets, allPossiblePopulateCoords, allParityTargets
     * to inform the shooting and population functions
     */
    private void fillWithAllCoords() {
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                Coordinates createdCoord = new Coordinates(row, col);
                allPossibleTargets.add(createdCoord);
                allPossiblePopulateCoords.add(createdCoord);
                if (hasParity(row, col)) {
                    allParityTargets.add(createdCoord);
                }
            }
        }
        java.util.Collections.shuffle(allPossibleTargets, randGen);
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
        java.util.Collections.shuffle(allParityTargets, randGen);
    }

    /**************************************************************
     * Draws and returns valid Coordinate to be shot at.
     * Adds Coordinates into targetHistory
     * Called by battle manager
     */
    public Coordinates enemyShootAt() {
        Coordinates newTarget;

        if (watchlist.isEmpty()) {
            newTarget = drawPartityTarget();
            logger.info(String.format("++++++++WATCHLIST EMPTY " + "enemy shoot parity: " + newTarget.toString()));

        } else {
            newTarget = drawFromWatchList();
            logger.info(String.format("++++++++WATCHLIST STUFFED " + "enemy shoot watched: " + newTarget.toString()));

        }
        modeCleanup(newTarget);

        this.addToTargetHistory(newTarget);
        return newTarget;

    }
    /************************************************
     * draws a valid Coord with parity filter on
     */
    private Coordinates drawPartityTarget() {
        java.util.Collections.shuffle(allParityTargets, randGen);
        return allParityTargets.get(0);
    }


    /************************************************
     * draws a valid Coord with from watchlist
     */
    private Coordinates drawFromWatchList() {
        return watchlist.pop();
    }


    /**********************************************************************
     * initialises the enemy mapGrid with its own ships randomly
     * is part of enemy constructor
     */
    private void populateEnemyMap() {

        int numDestroyer = this.getFleet().getNumDestroyer();
        int numCruiser = this.getFleet().getNumCruiser();

        placeAirCraftCarrier();
        placeMultipleDestroyerAndCruiser(numDestroyer, "Destroyer", 3);
        placeMultipleDestroyerAndCruiser(numCruiser, "Cruiser", 2);
    }

    /***********************************************************************
     * pulls a head coord from allPossiblePopulateCoords
     * and justifies the coord before using it as a head for the aircraftCarrier
     * before placing the aircraftCarrier onto enemy map
     */
    private void placeAirCraftCarrier() {
        Orientation useOrientation = generateOrientation();
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen);
        Coordinates useCoord = allPossiblePopulateCoords.get(0);
        Index row = useCoord.getRowIndex();
        Index col = useCoord.getColIndex();
        int justifiedCoord; //zero-based
        Coordinates currentBattleshipHead;

        if (useOrientation.isHorizontal()) { //rectify X coord of head, Y doesn't matter (all Y will work)
            justifiedCoord = justifyCoord(col.getZeroBased()); //col is justified
            currentBattleshipHead = new Coordinates(row.getZeroBased(), justifiedCoord);
        } else {
            justifiedCoord = justifyCoord(row.getZeroBased()); //row gets justified
            currentBattleshipHead = new Coordinates(justifiedCoord, col.getZeroBased());
        }
        Name currentBattleshipName = new Name("enemyAircraftCarrier");
        Battleship currentBattleship = new Battleship(currentBattleshipName, 5, 5);
        // public Battleship(Name name, int length, int life, Set<Tag> tags) {
        //should have no error, since it is the first ship placed, and all map sizes have a max of 1 aircraft carrier
        //AircraftCarrierBattleship x = new AircraftCarrierBattleship(currentBattleshipName);
        this.getMapGrid().putShip(currentBattleship, currentBattleshipHead, useOrientation);
        markAsOccupied(currentBattleshipHead, 5, useOrientation);
        logger.info(String.format("++++++++POPULATED aircraft carrier at " + currentBattleshipHead.toString()
                + " orientation is " + useOrientation.toString()));
    }

    /*****************************************************************************
     * places all destroyers and cruisers onto enemy map
     * and marks those occupied cells in allPossiblePopulateCoords
     */
    private void placeMultipleDestroyerAndCruiser(int numShips, String shipType, int shipSize) {
        Orientation useOrientation;
        Coordinates useCoord;
        ArrayList<Battleship> preppedShips = generateBattleships(numShips, shipType, shipSize);

        while (!preppedShips.isEmpty()) {
            try {
                useOrientation = generateOrientation();
                java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
                useCoord = allPossiblePopulateCoords.get(0);
                Battleship useShip = preppedShips.get(0);
                this.getMapGrid().putShip(useShip, useCoord, useOrientation);
                preppedShips.remove(0);
                markAsOccupied(useCoord, shipSize, useOrientation);
                logger.info(String.format("++++++++POPULATED " + useShip.getName() + "at "
                        + useCoord.toString()
                        + " orientation is " + useOrientation.toString()));
            } catch (ArrayIndexOutOfBoundsException e) {
                //TODO log the error later from putship
            }
        }

    }



    /************************************************************************
     * creates list of a certain battleship type
     * to be put on map
     */
    private ArrayList<Battleship> generateBattleships (int numShips, String battleshipType, int shipSize) {
        ArrayList<Battleship> preppedFleet = new ArrayList<>();
        for (int i = 1; i <= numShips; i++) {
            Name currentBattleshipName = new Name("enemy" + battleshipType + i);
            Battleship currentBattleship = new Battleship(currentBattleshipName, shipSize, shipSize);
            preppedFleet.add(currentBattleship);
        }
        logger.info(String.format("++++++++GENERATED: " + preppedFleet.toString()));
        return preppedFleet;
    }
    /***************************************************************
     * removes all the occupied Coords from allPossiblePopulateCoords
     * when a new ship is placed
     */
    private void markAsOccupied(Coordinates head, int shipSize, Orientation useOrientation) {

        //increase col
        if (useOrientation.isHorizontal()) { //row stays the same
            int colStart = head.getColIndex().getZeroBased();
            for (int i = 0; i < shipSize; i++) {
                Coordinates markedCoord = new Coordinates(head.getRowIndex().getZeroBased(), colStart + i);
                allPossiblePopulateCoords.remove(markedCoord);
                logger.info(String.format("++++++++MARK_AS_OCCUPIED: " + markedCoord.toString()));
            }
        } else { //increase row
            for (int i = 0; i < shipSize; i++) { //col stays the same
                int rowStart = head.getRowIndex().getZeroBased();
                Coordinates markedCoord = new Coordinates(rowStart + i, head.getColIndex().getZeroBased());
                allPossiblePopulateCoords.remove(markedCoord);
                logger.info(String.format("++++++++MARK_AS_OCCUPIED: " + markedCoord.toString()));

            }
        }
    }

    /*****************************************************************
     * justifies the head coord given to ensure
     * ship to be placed can fit the map boundaries
     */
    private int justifyCoord(int coordToJustify) {
        int lowerHalfceiling = (mapSize / 2) - 1;
        switch (mapSize) {
        case 6: //ceiling = 0 to 2, 3
            if (coordToJustify <= lowerHalfceiling) {
                coordToJustify = 0;
            } else {
                coordToJustify = 1;
            }
            break;
        case 10: //ceiling = 0 to 5, 6
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


    /********************************************************************************
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

    /*********************************************************************
     * receives status of an attacked cell from Battle manager
     */
    public void receiveStatus(Status latestStatusInfo) {
        lastAttackStatus = latestStatusInfo;
        logger.info(String.format("++++++++RECEIVE_STATUS: " + lastCoordAttacked.toString()
                + " status: " + latestStatusInfo.toString()));
        updateWatchlist(lastCoordAttacked);
    }


    /*************************************************************************
     * Add coords of the cardinal positions to the last attacked cell
     * to the watchlist
     */
    private void updateWatchlist(Coordinates lastCoordAttacked) {
        logger.info(String.format("++++++++BEFORE_UPDATE_WATCHLIST_STATUS_CHECK " + lastCoordAttacked.toString()
                + " status: " + lastAttackStatus.toString()));
        if (lastAttackStatus == Status.SHIPHIT) {
            int oldRow = lastCoordAttacked.getRowIndex().getZeroBased();
            int oldCol = lastCoordAttacked.getColIndex().getZeroBased();
            Coordinates updatedCoord = new Coordinates(0, 0);
            boolean hasCardinals = false;

            //ADD CARDINAL DIRECTIONS TO WATCHLIST.
            //WATCHLIST COORDS IN FORM a1

            if (oldRow - 1 >= 0) {
                //add cardinal NORTH to watchlist. ROW MINUS ONE
                updatedCoord = new Coordinates(oldRow - 1, oldCol);
                hasCardinals = true;
            }
            if (oldRow + 1 < mapSize) {
                //add cardinal SOUTH to watchlist  ROW PLUS ONE
                updatedCoord = new Coordinates(oldRow + 1, oldCol);
                hasCardinals = true;
            }
            if (oldCol - 1 >= 0) {
                //add cardinal WEST to watchlist   COL MINUS ONE
                updatedCoord = new Coordinates(oldRow, oldCol - 1);
                hasCardinals = true;
            }
            if (oldCol + 1 < mapSize) {
                //add cardinal EAST to watchlist   COL PLUS ONE
                updatedCoord = new Coordinates(oldRow, oldCol + 1);
                hasCardinals = true;

            }
            if (isValidCardinal(updatedCoord) && hasCardinals) {
                watchlist.push(updatedCoord);
            }
            logger.info(String.format("++++++++WATCHLIST UPDATING:\n" + watchlist.toString()));
            logger.info(String.format("++++++++WATCHLIST SIZE:\n" + watchlist.size()));

        }

    }

    private boolean isValidCardinal(Coordinates useCoord) {
        return allPossibleTargets.contains(useCoord) || allParityTargets.contains(useCoord);
    }

    /******************************************************8
     * Remove the last used coord
     * from allParityTargets and allPossibleTargets
     */
    private void modeCleanup(Coordinates usedCoord) {
        allParityTargets.remove(usedCoord);
        allPossibleTargets.remove(usedCoord);
        lastCoordAttacked = usedCoord;
    }

    /*************************************************************
     * checks that coord is made up of one odd and one even x-y coordinate pair
     * and returns true if so
     */
    private boolean hasParity (int row, int col) {

        int parity = (row % 2) + (col % 2);
        return (parity == 1);
    }
}
