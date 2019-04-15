package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.GenerateBillForReservationCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillManager;
import seedu.hms.model.BillModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.bill.Bill;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.ReservationContainsPayerPredicate;
import seedu.hms.model.reservation.ReservationWithDatePredicate;
import seedu.hms.model.reservation.ReservationWithTypePredicate;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.DateRange;

/**
 * Parses input arguments and creates a new GenerateBillForReservationCommand object
 */
public class GenerateBillForReservationCommandParser implements Parser<GenerateBillForReservationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GenerateBillForReservationCommand
     * and returns an GenerateBillForReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForReservationCommand parse(String args, CustomerModel customerModel, BillModel billModel,
                                                   ReservationModel reservationModel)
        throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATES);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateBillForReservationCommand.MESSAGE_USAGE));
        }

        List<Customer> lastShownList = customerModel.getFilteredCustomerList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        //Finds the selected customer
        Customer customer = lastShownList.get(index.getZeroBased());
        IdentificationNo payerIdentificationNo = customer.getIdNum();
        String payerId = payerIdentificationNo.toString();

        ReservationContainsPayerPredicate reservationContainsPayerPredicate =
            new ReservationContainsPayerPredicate(payerId);


        ReservationWithTypePredicate reservationWithTypePredicate;
        if (argMultimap.getValue(PREFIX_ROOM).isPresent()) {
            reservationWithTypePredicate = new ReservationWithTypePredicate(
                ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get(), reservationModel).getName());
        } else {
            reservationWithTypePredicate = new ReservationWithTypePredicate("");
        }

        //Search in whole day if timing is not provided
        String currentDate = Integer.toString(Calendar.getInstance().getTime().getDate());
        String currentMonth = Integer.toString(Calendar.getInstance().getTime().getMonth() + 1);
        String currentYear = Integer.toString(Calendar.getInstance().getTime().getYear() + 1900);
        String currentDay = String.format("%s/%s/%s", currentDate, currentMonth, currentYear);

        Calendar oneYearAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 364; i++) {
            oneYearAfterCurrentDate.setTimeInMillis(
                oneYearAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }
        String oneYearAfterCurrentDateDate = Integer.toString(oneYearAfterCurrentDate.getTime().getDate());
        String oneYearAfterCurrentDateMonth = Integer.toString(oneYearAfterCurrentDate.getTime().getMonth() + 1);
        String oneYearAfterCurrentDateYear = Integer.toString(oneYearAfterCurrentDate.getTime().getYear() + 1900);
        String oneYearAfterCurrentDay = String.format("%s/%s/%s", oneYearAfterCurrentDateDate,
            oneYearAfterCurrentDateMonth, oneYearAfterCurrentDateYear);

        DateRange dateRange = ParserUtil.parseDates(argMultimap.getValue(PREFIX_DATES)
            .orElse(currentDay + "-" + oneYearAfterCurrentDay));
        ReservationWithDatePredicate reservationWithDatePredicate =
            new ReservationWithDatePredicate(dateRange);

        //Reservation bill
        Predicate<Reservation> reservationPredicate;
        reservationPredicate = (reservationTested) -> reservationContainsPayerPredicate.test(reservationTested)
            && reservationWithTypePredicate.test(reservationTested)
            && reservationWithDatePredicate.test(reservationTested);
        billModel.updateFilteredReservationList(reservationPredicate);
        ObservableList<Reservation> reservationObservableList = billModel.getFilteredReservationList();
        HashMap<RoomType, Pair<Double, Long>> reservationBill =
            billModel.generateHashMapForReservation(reservationObservableList);

        Bill bill = new Bill(customer, new HashMap<>(), reservationBill);
        return new GenerateBillForReservationCommand(reservationContainsPayerPredicate,
            reservationWithTypePredicate, reservationWithDatePredicate,
            bill);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateBillForReservationCommand parse(String args) throws ParseException {
        return parse(args, new CustomerManager(), new BillManager(), new ReservationManager());
    }

}
