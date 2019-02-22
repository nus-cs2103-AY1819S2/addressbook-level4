package seedu.address.testutil;

import seedu.address.model.CardFolder;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CardFolder ab = new CardFolderBuilder().withPerson("John", "Doe").build();}
 */
public class CardFolderBuilder {

    private CardFolder cardFolder;

    public CardFolderBuilder() {
        cardFolder = new CardFolder();
    }

    public CardFolderBuilder(CardFolder cardFolder) {
        this.cardFolder = cardFolder;
    }

    /**
     * Adds a new {@code Person} to the {@code CardFolder} that we are building.
     */
    public CardFolderBuilder withPerson(Person person) {
        cardFolder.addPerson(person);
        return this;
    }

    public CardFolder build() {
        return cardFolder;
    }
}
