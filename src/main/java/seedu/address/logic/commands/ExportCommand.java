package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.io.*;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.person.Person;

public class ExportCommand extends Command {

    public final String file_path;

    public final String file_name;

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export person informaction to csv file";

    public static final String MESSAGE_SUCCESS = "Successful exported! ";

    public ExportCommand(String path, String name) {

        this.file_path = path;
        this.file_name = name;
    }

    public static String getRecord(Person P) {

        String record = P.getName() + "," + P.getPhone() + "," + P.getAddress() + "," + P.getEmail() + ","
                        + P.getSalary() + "," + P.getOccupation() + "," + P.getRelationship();
        return record;
    }

    public static boolean exportCSV(File file, ObservableList<Person> dataList) {

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        boolean isSuccess = false;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw = new BufferedWriter(new FileWriter(file));
            if (dataList != null && !dataList.isEmpty()) {
                for (Person P : dataList) {
                    String record = getRecord(P);
                    bw.write(record);
                    bw.newLine();
                }
            }
            isSuccess = true;
        } catch (Exception e)
        {
            isSuccess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return isSuccess;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<Person> P = model.getFilteredPersonList();
        String full_file = this.file_path+"/"+this.file_name+".csv";
        File file = new File(full_file);
        boolean success = exportCSV(file,P);
        if(success != true) {
            throw new CommandException("Failed");
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
