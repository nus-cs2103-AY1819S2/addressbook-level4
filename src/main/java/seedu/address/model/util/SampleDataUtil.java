package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DocX;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.FutureAppointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Specialisation;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code docX} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Tan Ah Ming"), new Gender("M"), new Age("29"), new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29 #06-40"),
                getTagSet("foodpoisoning", "headache")),
            new Patient(new Name("Bernice Yu Ye Sim"), new Gender("F"), new Age("23"), new Phone("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens #07-18"),
                getTagSet("highbloodpressure", "stroke")),
            new Patient(new Name("David Li"), new Gender("M"), new Age("53"), new Phone("91031282"),
                new Address("Blk 436 Serangoon Gardens Street 26 #16-43"),
                getTagSet("diabetes", "stroke")),
            new Patient(new Name("Charlie Oldman"), new Gender("M"), new Age("101"), new Phone("93210283"),
                    new Address("Blk 11 Ang Mo Kio Street 74 #11-04"),
                    getTagSet()),
            new Patient(new Name("Irfan Ibrahim"), new Gender("M"), new Age("60"), new Phone("92492021"),
                new Address("Blk 47 Tampines Street 20 #17-35"),
                getTagSet("lungcancer", "fever")),
            new Patient(new Name("Roy Balakrishnan"), new Gender("M"), new Age("6"), new Phone("92624417"),
                new Address("Blk 45 Aljunied Street 85 #11-31"),
                getTagSet("flu"))
        };
    }

    public static Doctor[] getSampleDoctors() {
        return new Doctor[] {
            new Doctor(new Name("Alvin Tong"), new Phone("82352312"), new Gender("M"), new Year("2"),
                    getSpecSet("acupuncture")),
            new Doctor(new Name("Barney Ong"), new Phone("90534873"), new Gender("F"), new Year("22"),
                    getSpecSet("acupuncture", "general")),
            new Doctor(new Name("Cinderella Tan"), new Phone("90153481"), new Gender("F"), new Year("4"),
                    getSpecSet("massage")),
            new Doctor(new Name("Dominic Gong"), new Phone("92343211"), new Gender("M"), new Year("8"),
                    getSpecSet("massage", "acupuncture")),
            new Doctor(new Name("Ibe Tok"), new Phone("92492021"), new Gender("M"), new Year("11"),
                    getSpecSet("acupuncture")),
            new Doctor(new Name("Gabriel Teo"), new Phone("92624417"), new Gender("M"), new Year("5"),
                    getSpecSet("general"))
        };
    }

    public static MedicalHistory[] getSampleMedHists() {
        return new MedicalHistory[] {
            new MedicalHistory(new PersonId("1"), new PersonId("7"),
                    new ValidDate("2019-03-03"), new WriteUp("The patient has a high fever."))
        };
    }

    public static ReadOnlyDocX getSampleDocX() {
        DocX sampleDocX = new DocX();
        for (Patient samplePatient : getSamplePatients()) {
            sampleDocX.addPatient(samplePatient);
        }

        for (Doctor sampleDoctor : getSampleDoctors()) {
            sampleDocX.addDoctor(sampleDoctor);
        }

        for (MedicalHistory sampleMedHist : getSampleMedHists()) {
            sampleDocX.addMedHist(sampleMedHist);
        }

        return sampleDocX;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<Specialisation> getSpecSet(String... strings) {
        return Arrays.stream(strings)
                .map(Specialisation::new)
                .collect(Collectors.toSet());
    }

    public static List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        FutureAppointment appointment1 = new FutureAppointment(new AppointmentPatientId("1"),
                new AppointmentDoctorId("2"), new AppointmentDate("2016-06-01"), new AppointmentTime("09:00"));
        FutureAppointment appointment2 = new FutureAppointment(new AppointmentPatientId("1"),
                new AppointmentDoctorId("3"), new AppointmentDate("2016-06-01"), new AppointmentTime("10:00"));
        appointments.add(appointment1);
        appointments.add(appointment2);

        return appointments;
    }
}
