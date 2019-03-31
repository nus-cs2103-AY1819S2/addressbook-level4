package seedu.address.logic.comparators;

import java.util.Comparator;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Patient;

/**
 * Contains comparators for each patient attribute
 */
public class PatientComparator {

    /**
     * Comparator to sort via Name
     */
    private static Comparator<Patient> compPatientName = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getName().toString().compareTo(p2.getName().toString());
        }
    };

    /**
     * Comparator to sort via Phone number
     */
    private static Comparator<Patient> compPatientPhone = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getPhone().toString().compareTo(p2.getPhone().toString());
        }
    };

    /**
     * Comparator to sort via Email.
     */
    private static Comparator<Patient> compPatientEmail = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getEmail().toString().compareTo(p2.getEmail().toString());
        }
    };

    /**
     * Comparator to sort via Address.
     */
    private static Comparator<Patient> compPatientAddress = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getAddress().toString().compareTo(p2.getAddress().toString());
        }
    };

    /**
     * Comparator to sort via Nric.
     */
    private static Comparator<Patient> compPatientNric = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getNric().toString().compareTo(p2.getNric().toString());
        }
    };

    /**
     * Comparator to sort via Date of Birth.
     */
    private static Comparator<Patient> compPatientDob = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p2.getDateOfBirth().compareTo(p1.getDateOfBirth());
        }
    };

    /**
     *  Comparator to sort via Sex.
     */
    private static Comparator<Patient> compPatientSex = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getSex().getSex().compareTo(p2.getSex().getSex());
        }
    };

    /**
     *  Comparator to sort via Drug Allergy.
     */
    private static Comparator<Patient> compPatientDrug = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getDrugAllergy().toString().compareTo(p2.getDrugAllergy().toString());
        }
    };

    /**
     *  Comparator to sort via Description.
     */
    private static Comparator<Patient> compPatientDesc = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getPatientDesc().toString().compareTo(p2.getPatientDesc().toString());
        }
    };

    /**
     *  Comparator to sort via NextOfKin's Name.
     */
    private static Comparator<Patient> compPatientKinName = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getNextOfKin().getName().toString().compareTo(p2.getNextOfKin().getName().toString());
        }
    };

    /**
     *  Comparator to sort via NextOfKin's Relation
     */
    private static Comparator<Patient> compPatientKinRelation = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getNextOfKin().getKinRelation().toString()
                .compareTo(p2.getNextOfKin().getKinRelation().toString());
        }
    };

    /**
     *  Comparator to sort via NextOfKin's Phone
     */
    private static Comparator<Patient> compPatientKinPhone = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getNextOfKin().getPhone().toString().compareTo(p2.getNextOfKin().getPhone().toString());
        }
    };

    /**
     *  Comparator to sort via NextOfKin's Address.
     */
    private static Comparator<Patient> compPatientKinAddress = new Comparator<Patient>() {
        @Override
        public int compare(Patient p1, Patient p2) {
            return p1.getNextOfKin().getAddress().toString().compareTo(p2.getNextOfKin().getAddress().toString());
        }
    };

    public static Comparator<Patient> getPatientComparator(String parameterType) throws ParseException {
        Comparator<Patient> paComp;
        switch (parameterType.trim()) {

        case "name":
            paComp = compPatientName;
            break;

        case "phone":
            paComp = compPatientPhone;
            break;

        case "email":
            paComp = compPatientEmail;
            break;

        case "address":
            paComp = compPatientAddress;
            break;

        case "nric":
            paComp = compPatientNric;
            break;

        case "dob":
            paComp = compPatientDob;
            break;

        case "sex":
            paComp = compPatientSex;
            break;

        case "drug":
            paComp = compPatientDrug;
            break;

        case "desc":
            paComp = compPatientDesc;
            break;

        case "kinN":
            paComp = compPatientKinName;
            break;

        case "kinR":
            paComp = compPatientKinRelation;
            break;

        case "kinP":
            paComp = compPatientKinPhone;
            break;

        case "kinA":
            paComp = compPatientKinAddress;
            break;

        default:
            throw new ParseException("");
        }

        return paComp;
    }
}
