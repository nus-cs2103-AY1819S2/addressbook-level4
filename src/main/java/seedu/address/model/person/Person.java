package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final MatricNumber matricNumber;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Gender gender;
    private final YearOfStudy yearOfStudy;
    private final Major major;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, MatricNumber matricNumber, Phone phone, Email email, Address address, Gender gender,
                  YearOfStudy yearOfStudy, Major major, Set<Tag> tags) {
        requireAllNonNull(name, matricNumber, phone, email, address, gender, yearOfStudy, major, tags);
        this.name = name;
        this.matricNumber = matricNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public MatricNumber getMatricNumber() {
        return matricNumber;
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

    public Gender getGender() {
        return gender;
    }

    public YearOfStudy getYearOfStudy() {
        return yearOfStudy;
    }

    public Major getMajor() {
        return major;
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
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
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
                && otherPerson.getMatricNumber().equals(getMatricNumber())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getGender().equals(getGender())
                && otherPerson.getYearOfStudy().equals(getYearOfStudy())
                && otherPerson.getMajor().equals(getMajor())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(address, name, phone, email, gender, major, matricNumber, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Gender: ")
                .append(getGender())
                .append(" Major: ")
                .append(getMajor())
                .append(" Matric Number: ")
                .append(getMatricNumber())
                .append(" Year of Study: ")
                .append(getYearOfStudy())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
