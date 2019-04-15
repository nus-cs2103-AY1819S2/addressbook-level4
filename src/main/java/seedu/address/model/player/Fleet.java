package seedu.address.model.player;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

/**
 * Represents a fleet of ships in the player.
 */
public class Fleet {
    private ArrayList<FleetEntry> deployedFleet;

    private final int numAircraftCarrier;
    private final int numCruiser;
    private final int numDestroyer;

    private int numDestroyerLeft;
    private int numCruiserLeft;
    private int numAircraftCarrierLeft;

    /**
     * Default constructor for a fleet of size 8 with placeholder ship names.
     */
    public Fleet() {
        this(5, 2, 1);
    }

    /**
     * Constructor using formula according to mapSize.
     *
     * @param mapSize size of map grid.
     */
    public Fleet (int mapSize) {
        this((mapSize + 2) / 5, mapSize - 5, 1);
    }

    /**
     * Constructor for a fleet with placeholder ship names.
     *
     * @param numDestroyer number of destroyers.
     * @param numCruiser number of cruisers.
     * @param numAircraftCarrier number of aircraft carriers.
     */
    public Fleet(int numDestroyer, int numCruiser, int numAircraftCarrier)
            throws IllegalArgumentException {

        if (numDestroyer + numCruiser + numAircraftCarrier <= 0) {
            throw new IllegalArgumentException();
        }

        this.numAircraftCarrier = numAircraftCarrier;
        this.numCruiser = numCruiser;
        this.numDestroyer = numDestroyer;

        this.numDestroyerLeft = numDestroyer;
        this.numCruiserLeft = numCruiser;
        this.numAircraftCarrierLeft = numAircraftCarrier;

        this.deployedFleet = new ArrayList<>();
    }

    /**
     * Calculates the number of ships in the deployed fleet.
     *
     * @return size of the fleet.
     */
    public int getSize() {
        return this.numDestroyerLeft + this.numCruiserLeft + this.numAircraftCarrierLeft;
    }

    public ArrayList<FleetEntry> getDeployedFleet() {
        return this.deployedFleet;
    }

    /**
     * Resets the fleet.
     *
     * @param mapSize size of the map grid.
     */
    public void resetFleet(int mapSize) {
        this.numDestroyerLeft = (mapSize + 2) / 5;
        this.numCruiserLeft = mapSize - 5;
        this.numAircraftCarrierLeft = 1;
        this.deployedFleet = new ArrayList<>();
    }

    /**
     * Deploys one ship. Checks class of ship to do so.
     *
     * @param battleship battleship object.
     * @param coordinates coordinates of the battleship.
     * @param orientation orientation of the battleship.
     */
    public void deployOneBattleship(Battleship battleship, Coordinates coordinates, Orientation orientation) {
        if (battleship instanceof DestroyerBattleship) {
            this.numDestroyerLeft--;
        } else if (battleship instanceof CruiserBattleship) {
            this.numCruiserLeft--;
        } else if (battleship instanceof AircraftCarrierBattleship) {
            this.numAircraftCarrierLeft--;
        }

        this.deployedFleet.add(new FleetEntry(
                battleship,
                coordinates,
                orientation));
    }

    /**
     * Checks if there are enough battleships. Returns true or false.
     *
     * @param battleship battleship object.
     * @param numBattleship number of battleships.
     * @return whether there are enough battleships.
     */
    public boolean isEnoughBattleship(Battleship battleship, int numBattleship) {
        if (battleship instanceof DestroyerBattleship) {
            return numBattleship <= this.getNumDestroyerLeft();
        } else if (battleship instanceof CruiserBattleship) {
            return numBattleship <= this.getNumCruiserLeft();
        } else if (battleship instanceof AircraftCarrierBattleship) {
            return numBattleship <= this.getNumAircraftCarrierLeft();
        }

        return false;
    }

    /**
     * Get list of battleship by class.
     *
     * @param battleshipClass class of battleship.
     * @return list of battleships that are of that class.
     */
    public List<FleetEntry> getListOfBattleship(Class battleshipClass) {
        return this.deployedFleet
                .stream()
                .filter(entry -> entry.getBattleship().getClass().equals(battleshipClass))
                .collect(Collectors.toList());
    }

    /**
     * Getter method for number of destroyers left.
     *
     * @return number of destroyers left.
     */
    public int getNumDestroyerLeft() {
        return this.numDestroyerLeft;
    }

    /**
     * Getter method for number of cruisers left.
     *
     * @return number of cruisers left.
     */
    public int getNumCruiserLeft() {
        return this.numCruiserLeft;
    }

    /**
     * Getter method for number of aircraft carriers left.
     *
     * @return number of aircraft carriers left.
     */
    public int getNumAircraftCarrierLeft() {
        return this.numAircraftCarrierLeft;
    }

    /**
     * Gets all tags that have been used by all battleships in the fleet.
     *
     * @return set of all tags used by all battleships in the fleet.
     */
    public Set<Tag> getAllTags() {
        Set<Tag> tagSet = new HashSet<>();

        for (FleetEntry fleetEntry : this.getDeployedFleet()) {
            for (Tag tag : fleetEntry.getBattleship().getTags()) {
                tagSet.add(tag);
            }
        }

        return tagSet;
    }

    /**
     * Gets list of {@code FleetEntry}s that have {@code Battleship}s containing all
     * the specified tags from the fleet.
     *
     * @param tagSet set of tags.
     * @return list of fleet entries that have battleships matching all the tags.
     */
    public List<FleetEntry> getByTags(Set<Tag> tagSet) {
        return this.getDeployedFleet().stream()
                .filter(fleetEntry -> fleetEntry.getBattleship().getTags().containsAll(tagSet))
                .collect(Collectors.toList());
    }

    /**
     * Gets list of {@code FleetEntry}s that have {@code Battleship}s containing all
     * the specified name from the fleet.
     *
     * @param name name of battleship.
     * @return list of fleet entries that have battleships matching the name.
     */
    public List<FleetEntry> getByName(Name name) {
        return this.getDeployedFleet().stream()
                .filter(fleetEntry -> fleetEntry.getBattleship().getName().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Gets list of {@code FleetEntry}s that have {@code Battleship}s containing all
     * the specified names from the fleet.
     *
     * @param nameSet set of names of battleships.
     * @return list of fleet entries that have battleships matching the names.
     */
    public List<FleetEntry> getByNames(Set<Name> nameSet) {
        ArrayList<FleetEntry> result = new ArrayList<>();

        for (FleetEntry fleetEntry : this.deployedFleet) {
            if (nameSet.contains(fleetEntry.getBattleship().getName())) {
                result.add(fleetEntry);
            }
        }

        return result;
    }

    /**
     * Checks if all battleships have been deployed.
     *
     * @return boolean of whether all battleships have been deployed.
     */
    public boolean isAllDeployed() {
        return this.getListOfBattleship(DestroyerBattleship.class).size() == this.numDestroyer
                && this.getListOfBattleship(CruiserBattleship.class).size() == this.numCruiser
                && this.getListOfBattleship(AircraftCarrierBattleship.class).size() == this.numAircraftCarrier;
    }

    /**
     * Checks if all the battleships in a deployed fleet are destroyed.
     *
     * @return boolean of whether all battleships have zero life, i.e., are destroyed.
     */
    public boolean isAllDestroyed() {
        boolean isAllDestroyed = true;

        for (FleetEntry fleetEntry : this.getDeployedFleet()) {
            if (!fleetEntry.getBattleship().isDestroyed()) {
                isAllDestroyed = false;
            }
        }

        return isAllDestroyed;
    }

    /**
     * Gets the fleet as a string containing information about its size and contents.
     *
     * @return string of fleet size and contents.
     */
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

    /**
     * Represents an entry in the fleet. To describe the orientation and coordinates
     * of a given battleship.
     */
    public class FleetEntry {
        private final Battleship battleship;
        private final Orientation orientation;
        private final Coordinates coordinates;

        /**
         * Default constructor method.
         *
         * @param battleship battleship object.
         * @param coordinates coordinates of the battleship on the map grid.
         * @param orientation orientation of the battleship on the map grid.
         */
        public FleetEntry(Battleship battleship, Coordinates coordinates, Orientation orientation) {
            this.battleship = battleship;
            this.coordinates = coordinates;
            this.orientation = orientation;
        }

        /**
         * Getter method for battleship.
         *
         * @return battleship.
         */
        public Battleship getBattleship() {
            return battleship;
        }

        /**
         * Getter method for coordinates.
         *
         * @return coordinates of the battleship.
         */
        public Coordinates getCoordinates() {
            return coordinates;
        }

        /**
         * Getter method for orientation.
         *
         * @return orientation of the battleship.
         */
        public Orientation getOrientation() {
            return orientation;
        }

        /**
         * String containing information about the {@code FleetEntry}, including the
         * battleship, its coordinates and its orientation on the map grid.
         *
         * @return string with information about the fleet entry.
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.getBattleship())
                    .append(" (")
                    .append(this.getBattleship().getLife())
                    .append("/")
                    .append(this.getBattleship().getLength())
                    .append(")")
                    .append(" at ")
                    .append(this.getCoordinates())
                    .append(" ")
                    .append(this.getOrientation())
                    .append(" ");
            this.getBattleship().getTags().forEach(builder::append);
            return builder.toString();
        }

        /**
         * Checks if two given {@code FleetEntry}s are equal. Compares the battleship,
         * coordinates, and orientation.
         *
         * @param other other object.
         * @return boolean of whether the two objects are equal.
         */
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FleetEntry)) {
                return false;
            }

            // state check
            FleetEntry e = (FleetEntry) other;
            return this.battleship.equals(e.getBattleship())
                    && this.coordinates.equals(e.getCoordinates())
                    && this.orientation.equals(e.getOrientation());
        }
    }
}
