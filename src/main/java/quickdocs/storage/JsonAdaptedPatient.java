package quickdocs.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import quickdocs.commons.exceptions.IllegalValueException;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Patient}.
 */
public class JsonAdaptedPatient {

    private String name;
    private String nric;
    private String email;
    private String address;
    private String contact;
    private String gender;
    private String dob;
    private List<JsonAdaptedTag> tagList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("nric") String nric,
                              @JsonProperty("email") String email, @JsonProperty("address") String address,
                              @JsonProperty("contact") String contact, @JsonProperty("gender") String gender,
                              @JsonProperty("dob") String dob,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.dob = dob;
        if (tagged != null) {
            this.tagList.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {

        this.name = source.getName().getName();
        this.nric = source.getNric().getNric();
        this.email = source.getEmail().getEmail();
        this.address = source.getAddress().getAddress();
        this.contact = source.getContact().getContact();
        this.gender = source.getGender().getGender();
        this.dob = source.getDob().toString();
        tagList.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return a Patient model object
     * @throws IllegalValueException if there were any data constraints violated in the tags.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagList) {
            personTags.add(tag.toModelType());
        }

        final Name modelName = new Name(name);

        final Nric modelNric = new Nric(nric);

        final Email modelEmail = new Email(email);

        final Address modelAddress = new Address(address);

        final Contact modelContact = new Contact(contact);

        final Gender modelGender = new Gender(gender);

        final Dob modelDob = new Dob(dob);

        final ArrayList<Tag> modelTags = new ArrayList<>();

        for (JsonAdaptedTag tag : tagList) {
            modelTags.add(tag.toModelType());
        }

        return new Patient(modelName, modelNric, modelEmail, modelAddress, modelContact, modelGender,
                modelDob, modelTags);
    }

}
