package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.InfoPanel;

/**
 * Sets a reminder to go off after adding hh:mm to current time.
 */
public class SetReminderCommand extends Command {

    public static final String COMMAND_WORD = "setreminder";
    public static final String INVALID_TIME_FORMAT = "Reminder should only be in t/<hh:mm:ss> m/<message> format, with "
            + "time consisting of only digits.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a reminder to notify user after adding hh:mm:ss to the current time. \n"
            + "Reminder only goes off during this session state. It will disappear after exiting the app. \n"
            + "Example: " + COMMAND_WORD + " t/05:00:00 m/call patient Akshay for his request appointment.";

    public static final String MESSAGE_REMINDER_SUCCESS = "Set reminder to alert at: %1$s";

    public static final SimpleDateFormat DISPLAY_FORMATTER = new SimpleDateFormat("dd MMMM YYYY, hh:mm:ss a");
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("hh:mm:ss");
    private final String time;
    private final String message;
    private final Timer timer;

    public SetReminderCommand(String time, String message) {
        this.time = time;
        this.message = message;
        this.timer = new Timer();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            Date duration = FORMATTER.parse(time);

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = dateToCalendar(duration);
            Calendar cTotal = (Calendar) c1.clone();
            cTotal.add(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
            cTotal.add(Calendar.MINUTE, c2.get(Calendar.MINUTE));
            cTotal.add(Calendar.SECOND, c2.get(Calendar.SECOND));

            //System.out.println("Action scheduled to perform at: " + DISPLAY_FORMATTER.format(cTotal.getTime()));

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        Alert reminderAlert = new Alert(Alert.AlertType.INFORMATION);
                        reminderAlert.setTitle("Reminder Alert");
                        reminderAlert.setHeaderText("Health Hub Reminder Alert");
                        reminderAlert.setContentText("This is an alert dialog to remind you to "
                            + message);
                        DialogPane dialogPane = reminderAlert.getDialogPane();
                        dialogPane.getStylesheets().add(InfoPanel.STYLESHEET.toExternalForm());
                        dialogPane.getStyleClass().add("alertDialog");
                        Toolkit.getDefaultToolkit().beep();
                        reminderAlert.showAndWait();
                    });
                }
            }, cTotal.getTime());

            return new CommandResult(String.format(MESSAGE_REMINDER_SUCCESS,
                    DISPLAY_FORMATTER.format(cTotal.getTime())));

        } catch (ParseException parseException) {
            throw new CommandException("An unexpected error occurred!");
        }
    }


    /**
     * Converts a {@code Date} object to a {@code Calendar} instance
     * Reference: https://www.mkyong.com/java/java-convert-date-to-calendar-example/
     * @param date to be converted
     * @return a Calendar instance
     */
    public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetReminderCommand // instanceof handles nulls
                && time.equals(((SetReminderCommand) other).time)); // state check
    }
}
