package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.SkillsTag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // new identity fields
    private final Education education;
    private final Gpa gpa;

    //private final Skills skills;

    // Data fields
    private final Address address;
    private final LinkedHashSet<SkillsTag> tags = new LinkedHashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Education education, Gpa gpa, Address address,
                  Set<SkillsTag> tags) {

        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.education = education;
        this.gpa = gpa;
        this.address = address;
        this.tags.addAll(tags);
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

    public Education getEducation() {
        return education;
    }

    public Gpa getGpa() {
        return gpa;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<SkillsTag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the number of Skills a person has
     */
    public int getSkillsNumber() {
        return PersonUtil.getTagTypeNumber(getTags(), "s");
    }

    /**
     * Returns the number of Positions a person has
     */
    public int getPositionsNumber() {
        return PersonUtil.getTagTypeNumber(getTags(), "p");
    }

    /**
     * Returns the number of Endorsements a person has
     */
    public int getEndorsementsNumber() {
        return PersonUtil.getTagTypeNumber(getTags(), "e");
    }

    /**
     * Checks if a tag as a String parameter is contained in one of the tags of that person
     */
    public boolean isTagExist(String tag) {
        for (SkillsTag skill : tags) {
            if (skill.tagName.toLowerCase().contains(tag) || skill.tagName.contains(tag)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the string parameter is contained in one of the skills of the person
     */
    public boolean isSkillExist(String tag) {
        for (SkillsTag skill : tags) {
            if (skill.toString().charAt(1) == 's') {
                if (skill.toString().substring(3, skill.toString().length() - 1).trim().toLowerCase()
                        .contains(tag.trim().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the string parameter is contained in one of the positions of the person
     */
    public boolean isPositionExist(String tag) {
        for (SkillsTag pos : tags) {
            if (pos.toString().charAt(1) == 'p') {
                if (pos.toString().substring(3, pos.toString().length() - 1).trim().toLowerCase()
                        .contains(tag.trim().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
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
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, education, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Education: ")
                .append(getEducation())
                .append(" Gpa: ")
                .append(getGpa())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    public String tagsToString() {
        return getTags().toString();
    }

    public String namesToString() {
        return getName().toString();
    }

    /**
     * Returns just the surname of the Persons name as a string
     */
    public String surnamesToString() {
        String fullName = namesToString();
        int finalSpace = fullName.lastIndexOf(" ");
        return fullName.substring(finalSpace + 1);
    }

    public String gpaToString() {
        return getGpa().toString();
    }

    public String educationToString() {
        return getEducation().toString();
    }
}
