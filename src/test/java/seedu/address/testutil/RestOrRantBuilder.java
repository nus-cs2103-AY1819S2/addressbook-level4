package seedu.address.testutil;

import seedu.address.model.RestOrRant;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code RestOrRant ab = new RestOrRantBuilder().withPerson("John", "Doe").build();}
 */
public class RestOrRantBuilder {

    private RestOrRant restOrRant;

    public RestOrRantBuilder() {
        restOrRant = new RestOrRant();
    }

    public RestOrRantBuilder(RestOrRant restOrRant) {
        this.restOrRant = restOrRant;
    }

    /**
     * Adds a new {@code Person} to the {@code RestOrRant} that we are building.
     */
    public RestOrRantBuilder withPerson(Person person) {
        restOrRant.addPerson(person);
        return this;
    }

    public RestOrRant build() {
        return restOrRant;
    }
}
