package quickdocs.model.patient;

import java.util.ArrayList;

import quickdocs.model.tag.Tag;

/**
 * Represents a patient record in QuickDocs
 */
public class Patient {

    private Name name;
    private Nric nric;
    private Email email;
    private Address address;
    private Contact contact;
    private Gender gender;
    private Dob dob;
    private ArrayList<Tag> tagList;

    public Patient() {
    }

    public Patient(Name name, Nric nric, Email email, Address address, Contact contact,
                   Gender gender, Dob dob, ArrayList<Tag> tagList) {
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.dob = dob;
        this.tagList = tagList;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Gender getGender() {
        return gender;
    }

    public Dob getDob() {
        return dob;
    }

    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    /**
     * Checks another Patient object, and if both have same nric, then its the
     * same person
     * Every inhabitant of Singapore have a unique NRIC, despite similar names
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getNric().equals(nric);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Patient details for: " + nric + "\n");
        sb.append("Name: " + name + "\n");
        sb.append("Date of Birth: " + dob + "\n");
        sb.append("Gender: " + gender + "\n");
        sb.append("Contact: " + contact + "\n");
        sb.append("Email: " + email + "\n");
        sb.append("Address: " + address + "\n");
        sb.append("Tags: " + tagList.toString() + "\n");

        return sb.toString();
    }
}
