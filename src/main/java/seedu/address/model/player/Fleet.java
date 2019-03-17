package seedu.address.model.player;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;


/**
 * Represents a list of individual battleships
 */
public class Fleet {


    private ArrayList<Battleship> deployedFleet;

    private int numDestroyer;
    private int numCruiser;
    private int numAircraftCarrier;

    /**
     * Default constructor for a fleet of size 8 with placeholder ship names.
     */
    public Fleet() {
        this(5, 2, 1);
    }
    /**
     * Constructor for a fleet with placeholder ship names.
     */
    public Fleet(int numDestroyer, int numCruiser, int numAircraftCarrier)
            throws IllegalArgumentException {

        if (numDestroyer + numCruiser + numAircraftCarrier <= 0) {
            throw new IllegalArgumentException();
        }

        this.numDestroyer = numDestroyer;
        this.numCruiser = numCruiser;
        this.numAircraftCarrier = numAircraftCarrier;

        this.deployedFleet = new ArrayList<>();
    }

    public int getSize() {
        return this.numDestroyer + this.numCruiser + this.numAircraftCarrier;
    }

    public ArrayList getDeployedFleet() {
        return this.deployedFleet;
    }

    /**
     * Deploys one battleship. Checks class of battleship.
     */
    public void deployBattleship(Battleship battleship) throws Exception {
        if (battleship instanceof DestroyerBattleship) {
            deployDestroyerBattleships(1);
        } else if (battleship instanceof CruiserBattleship) {
            deployCruiserBattleships(1);
        } else if (battleship instanceof AircraftCarrierBattleship) {
            deployAircraftCarrierBattleships(1);
        }
    }

    /**
     * Deploys a destroyer.
     */
    public void deployDestroyerBattleships(int number)
            throws Exception {
        if (number > this.numDestroyer) {
            throw new Exception("Not enough destroyers.");
        } else {
            for (int i = 0; i < number; i++) {
                this.numDestroyer--;
                this.deployedFleet.add(new DestroyerBattleship());
            }
        }
    }

    /**
     * Deploys a cruiser.
     */
    public void deployCruiserBattleships(int number)
            throws Exception {
        if (number > this.numCruiser) {
            throw new Exception("Not enough cruisers.");
        } else {
            for (int i = 0; i < number; i++) {
                this.numCruiser--;
                this.deployedFleet.add(new CruiserBattleship());
            }
        }
    }

    /**
     * Deploys an aircraft carrier.
     */
    public void deployAircraftCarrierBattleships(int number)
            throws Exception {
        if (number > this.numAircraftCarrier) {
            throw new Exception("Not enough aircraft carriers.");
        } else {
            for (int i = 0; i < number; i++) {
                this.numAircraftCarrier--;
                this.deployedFleet.add(new AircraftCarrierBattleship());
            }
        }
    }

    public List<DestroyerBattleship> getListOfDestroyerBattleship() {
        return this.deployedFleet
                .stream()
                .filter(ship -> ship instanceof DestroyerBattleship)
                .map(ship -> (DestroyerBattleship) ship)
                .collect(Collectors.toList());
    }

    public List<CruiserBattleship> getListOfCruiserBattleship() {
        return this.deployedFleet
                .stream()
                .filter(ship -> ship instanceof CruiserBattleship)
                .map(ship -> (CruiserBattleship) ship)
                .collect(Collectors.toList());
    }

    public List<AircraftCarrierBattleship> getListOfAircraftCarrierBattleship() {
        return this.deployedFleet
                .stream()
                .filter(ship -> ship instanceof AircraftCarrierBattleship)
                .map(ship -> (AircraftCarrierBattleship) ship)
                .collect(Collectors.toList());
    }

    public int getNumDestroyer() {
        return this.numDestroyer;
    }

    public int getNumCruiser() {
        return this.numCruiser;
    }

    public int getNumAircraftCarrier() {
        return this.numAircraftCarrier;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getSize())
                .append(" Fleet size: ")
                .append(getSize())
                .append(" Fleet contents: ")
                .append(getDeployedFleet());
        return builder.toString();
    }

}
