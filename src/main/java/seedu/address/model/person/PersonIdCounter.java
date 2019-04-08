/* @@author wayneswq */
package seedu.address.model.person;

/**
 * This class is a singleton, only one instance can exist at any one time.
 * Represents the current maximum ID to represent the next unique patient or doctor.
 */
public final class PersonIdCounter {

    private static PersonIdCounter instance;
    private static int id = 0;

    private PersonIdCounter() {

    }

    public static PersonIdCounter getInstance() {
        if (instance == null) {
            instance = new PersonIdCounter();
        }

        return instance;
    }

    /**
     * Creates a new unique ID when called.
     */
    public PersonId generateNewId() {
        id += 1;
        return new PersonId(String.valueOf(id));
    }

    public void setCurrentMaxId(int id) {
        this.id = id;
    }

    public int getCurrentMaxId() {
        return id;
    }
}
