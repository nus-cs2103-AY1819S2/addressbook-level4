package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.tag.CopyTag;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    protected final Name name;
    protected final Phone phone;
    protected final Email email;

    // Data fields
    protected final Address address;
    protected final Set<Tag> tags = new HashSet<>();
    protected CopyTag copyInfo;
    protected int copyCount;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        copyInfo = null;
        copyCount = 0;
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Person personToCopy, int copyCount) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.copyCount = copyCount;
        copyInfo = new CopyTag(personToCopy, "$Copy" + copyCount);
        this.tags.add(copyInfo);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public boolean hasCopy() {
        return copyCount > 0;
    }

    /**
     *
     * @return true if a person has {@code Tag} copy
     */
    private boolean copyInTag() {
        for (Tag t : getTags()) {
            if (t.tagName.equals("Copy")) {
                return true;
            }
        }
        return false;
    }

    public boolean isCopy() {
        return copyInfo != null || copyInTag();
    }

    public void editCopy() {
        copyInfo.getOriginalPerson().edittedCopy();
    }

    private void edittedCopy() {
        copyCount -= 1;
    }

    /**
     * @return another instance of the same person
     * {@code Tag} Copy is added
     */
    public Person copy() {
        if (isCopy()) {
            return copyInfo.getOriginalPerson().copy();
        }
        copyCount++;
        return new Person(name, phone, email, address, tags, this, copyCount);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        throw new PersonIsNotPatient();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
