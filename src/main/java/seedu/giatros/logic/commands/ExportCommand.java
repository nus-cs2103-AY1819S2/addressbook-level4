package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_DEST;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;

/**
 * Exports current Giatros book as csv file to local disk.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Export succesful. Giatros book csv file located at ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports current Giatros book as csv file (giatrosbook.csv) to "
            + System.getProperty("user.home") + "/Giatros directory unless another "
            + "destination is specified by parameter.\n"
            + "Parameters: [" + PREFIX_DEST + "DESTINATION]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DEST + System.getProperty("user.home") + "/Desktop";

    public static final String MESSAGE_CSV_FAIL = "Unable to open giatrosbook.csv - invalid file location";
    public static final String INVALID_PATH = "Invalid path specified. Path must be non-empty and end in .csv";

    private static File curLocation;

    public ExportCommand(String destination) {
        curLocation = new File(destination);
    }

    public ExportCommand() {
        curLocation = new File(System.getProperty("user.home") + "/Giatros/giatrosbook.csv");

    }

    public static File getCurLocation() {

        return curLocation;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        try {

            File giatros = new File(model.getGiatrosBookFilePath().toAbsolutePath().toString());

            String giatrosData = FileUtils.readFileToString(giatros);

            InputStream is = new FileInputStream(String.valueOf(giatros));

            JSONTokener tokener = new JSONTokener(is);
            JSONObject object = new JSONObject(giatrosData);

            JSONArray patients = new JSONArray();
            JSONArray array = object.getJSONArray("patients");

            // Change array to force "name" column to be second column in the table
            for (int x = 0; x < array.length(); x++) {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("phone", array.getJSONObject(x).getString("phone"));
                map.put("address", array.getJSONObject(x).getString("address"));
                map.put("email", array.getJSONObject(x).getString("email"));
                map.put("appointments", String.valueOf(array.getJSONObject(x).getJSONArray("appointments")));
                map.put("name", array.getJSONObject(x).getString("name"));

                // Display allergies in neater string
                JSONArray jAllergies = array.getJSONObject(x).getJSONArray("allergies");
                String allergies = "";
                for (int i = 0; i < jAllergies.length(); i++) {
                    allergies += jAllergies.get(i) + ", ";
                }

                map.put("allergies", allergies);

                patients.put(map);
            }

            String dest = CDL.toString(patients);
            FileUtils.writeStringToFile(getCurLocation().getAbsoluteFile(), dest);

        } catch (IOException e) {
            new CommandException(MESSAGE_CSV_FAIL);
        }

        return new CommandResult(MESSAGE_SUCCESS + getCurLocation().getAbsolutePath());
    }
}
