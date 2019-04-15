package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.PdfUtil.saveMcPdfFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;

/**
 * Command to create a medical credit for selected record. The mc is saved as a pdf file.
 */
public class RecordMcCommand extends Command {
    public static final String COMMAND_WORD = "recordmc";
    public static final String COMMAND_WORD2 = "rmc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Create a pdf-format medical certificate file for selected record.\n"
            + "Parameters: INDEX (must be a positive integer) [Days suggested to rest]\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_SELECT_RECORD_SUCCESS = "MC created: %1$s";

    private final Index targetIndex;
    private final String daysToRest;

    public RecordMcCommand(Index targetIndex, String daysToRest) {
        this.targetIndex = targetIndex;
        this.daysToRest = daysToRest;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Record> filteredRecordList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= filteredRecordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Record record = filteredRecordList.get(targetIndex.getZeroBased());
        Patient patient = (Patient) model.getSelectedPerson();
        String mcNo;

        try {
            mcNo = createMcNo(record.getRecordDate().toString());
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        Path path = Paths.get("data", String.format("MC%s.pdf", mcNo));
        try {
            saveMcPdfFile(record, patient, daysToRest, mcNo, path);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SELECT_RECORD_SUCCESS, path.toString()));

    }

    /**
     * Create a mc serial number based on date and storage system
     * @param date
     * @return
     * @throws IOException
     */
    private String createMcNo(String date) throws IOException {
        for (int i = 0; i < 10000; i++) {
            String mcNo = date + "-" + i;
            String filePathString = Paths.get("data", String.format("MC%s.pdf", mcNo)).toString();
            System.out.println(filePathString);
            File f = new File(filePathString);
            if (!f.exists()) {
                return mcNo;
            }
        }
        throw new IOException("Too many MCs today");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordMcCommand // instanceof handles nulls
                && targetIndex.equals(((RecordMcCommand) other).targetIndex)); // state check
    }
}
