package quickdocs.model.patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import quickdocs.model.tag.Tag;

/**
 * Stores changes to patient records during an EditPatientCommand
 * All fields start out as null, and values can be assigned to the attributes using the
 * setter methods.
 *
 * During edit when the PatientEditedFields is compared with, the non-null values will
 * represent the changed attributes, and will be used to update the existing Patient Object.
 */
public class PatientEditedFields {

    private Name name;
    private Nric nric;
    private Email email;
    private Address address;
    private Contact contact;
    private Gender gender;
    private Dob dob;
    private ArrayList<Tag> tagList;

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Nric> getNric() {
        return Optional.ofNullable(nric);
    }

    public void setNric(Nric nric) {
        this.nric = nric;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Contact> getContact() {
        return Optional.ofNullable(contact);
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Optional<Gender> getGender() {
        return Optional.ofNullable(gender);
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Optional<Dob> getDob() {
        return Optional.ofNullable(dob);
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Optional<ArrayList<Tag>> getTagList() {
        return Optional.ofNullable(tagList);
    }

    public void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    /**
     * Checks if PatientEditedFields is empty, which means that no changes were made
     */
    public boolean checkEmpty() {
        return name == null && nric == null && email == null && address == null
                && contact == null && gender == null && dob == null && tagList == null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PatientEditedFields)) {
            return false;
        }

        PatientEditedFields otherPatient = (PatientEditedFields) other;
        return checkNonNullFields((PatientEditedFields) other);
    }

    /**
     * Checks if two PatientEditedFields are the same using non null fields
     *
     * @param other Another patientEditedFields object to test similarity
     * @return boolean value to indicate whether the two PatientEditedFields are the same based on
     * non null values
     */
    public boolean checkNonNullFields(PatientEditedFields other) {

        if ((name != null && other.getName().isPresent()) && !name.equals(other.getName().get())) {
            return false;
        }

        if ((nric != null && other.getNric().isPresent()) && !nric.equals(other.getNric().get())) {
            return false;
        }

        if ((email != null && other.getEmail().isPresent()) && !email.equals(other.getEmail().get())) {
            return false;
        }

        if ((address != null && other.getAddress().isPresent()) && !address.equals(other.getAddress().get())) {
            return false;
        }

        if ((contact != null && other.getContact().isPresent()) && !contact.equals(other.getContact().get())) {
            return false;
        }

        if ((gender != null && other.getGender().isPresent()) && !gender.equals(other.getGender().get())) {
            return false;
        }

        if ((dob != null && other.getDob().isPresent()) && !dob.equals(other.getDob().get())) {
            return false;
        }

        if (tagList != null && other.getTagList().isPresent()) {
            if (!Arrays.equals(tagList.toArray(), other.getTagList().get().toArray())) {
                return false;
            }
        }

        return true;
    }

}
