package quickdocs.logic.commands;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

import quickdocs.commons.core.LogsCenter;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.consultation.Consultation;

/**
 * List previous consultation sessions of a single patient if the NRIC of the patient
 * is supplied. If the index is supplied instead, then display the details of the
 * specific consultation session
 */
public class ListConsultationCommand extends Command {

    public static final String COMMAND_WORD = "listconsult";
    public static final String COMMAND_ALIAS = "lc";
    public static final String NO_RECORDS = "No past consultation records found.\n";
    public static final String INVALID_INDEX = "Index entered is invalid.\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": list all past consultation records of a single patient using patient's NRIC "
            + "or details of a single consultation record through its index.\n"
            + "Parameters: "
            + "INDEX OR NRIC\n"
            + "Example: " + COMMAND_WORD + " r/S9237161A\n"
            + "or: " + COMMAND_WORD + " 10\n";

    private static final Logger logger = LogsCenter.getLogger(ListConsultationCommand.class);

    private int index;
    private String nric;

    private int constructedBy;

    public ListConsultationCommand(int index) {
        this.index = index;
        constructedBy = 1;
        logger.info("Listing consultation using index");
    }

    public ListConsultationCommand(String value) {
        this.nric = value;
        constructedBy = 2;
        logger.info("Listing consultation using NRIC");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (constructedBy == 1 && (index > model.getConsultationList().size() || index < 1)) {
            throw new CommandException(INVALID_INDEX);
        }

        if (constructedBy == 1) {
            return new CommandResult(model.listConsultation(index).toString());
        }

        ArrayList<Consultation> consultations = model.listConsultation(nric);
        String result = listingConsultations(consultations);

        return new CommandResult(result);
    }

    /**
     * List all the past consultation records of a single patient
     */
    public String listingConsultations(ArrayList<Consultation> consultations) {
        StringBuilder sb = new StringBuilder();
        sb.append("Listing consultation records\n");
        sb.append("====================\n");

        if (consultations.isEmpty()) {
            sb.append(NO_RECORDS);
            return sb.toString();
        }

        for (Consultation con : consultations) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            sb.append(1 + con.getIndex() + ") ");
            sb.append(" visit for " + con.getDiagnosis().getAssessment() + " ");
            sb.append(" on " + con.getSession().format(formatter));
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getIndex() {
        return index;
    }

    public String getNric() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListConsultationCommand
                && checkEquals((ListConsultationCommand) other));
    }

    /**
     * Separate the checking of NRIC and index between 2 ListConsultationCommand
     *
     * @param other the other ListConsultationCommand to check equality against
     */
    public boolean checkEquals(ListConsultationCommand other) {

        if (nric == null && other.getNric() == null) {
            return getIndex() == other.getIndex();
        }

        return getNric().equals(other.getNric());
    }

}
