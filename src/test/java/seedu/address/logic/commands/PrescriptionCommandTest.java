package seedu.address.logic.commands;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class PrescriptionCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private Patient patient1;

    @Before
    public void init() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
        // to store medicine
        String[] paths = {"root"};
        modelManager.addMedicine("antibiotics", 2, paths, BigDecimal.valueOf(23.23));
    }

    @Test
    public void noConsultation() {
        // no consultation
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("antibiotics");
        qtyList.add(1);

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void noMedFound() {
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("nasal spray");
        qtyList.add(1);
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void insufficientMed() {
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("antibiotics");
        qtyList.add(3);
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void successfulPrescription() {
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("antibiotics");
        qtyList.add(1);
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));

        PrescriptionCommand prescriptionCommand;
        try {
            Medicine med = new Medicine("antibiotics", 2);
            prescriptionCommand = new PrescriptionCommand(medList, qtyList);
            StringBuilder sb = new StringBuilder();
            sb.append("prescription:\n");
            sb.append("====================\n");
            sb.append(new Prescription(med, qtyList.get(0)));
            org.junit.Assert.assertEquals(prescriptionCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());
        } catch (Exception ex) {
            org.junit.Assert.fail();
        }
    }

}
