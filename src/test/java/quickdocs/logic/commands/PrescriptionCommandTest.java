package quickdocs.logic.commands;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.ModelManager;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

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
    public void prescription_noOngoingConsultation_throwsCommandException() {
        // no consultation
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("antibiotics");
        qtyList.add(1);

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void prescription_medNotFound_throwsCommandException() {
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("nasal spray");
        qtyList.add(1);
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void prescription_insufficientMed_throwsCommandException() {
        ArrayList<String> medList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();
        medList.add("antibiotics");
        qtyList.add(3);
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));

        PrescriptionCommand prescriptionCommand = new PrescriptionCommand(medList, qtyList);

        Assert.assertThrows(CommandException.class, () -> prescriptionCommand.execute(modelManager, history));
    }

    @Test
    public void prescription_validPrescription_success() {
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
