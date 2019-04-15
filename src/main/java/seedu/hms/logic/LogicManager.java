package seedu.hms.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.logic.commands.BillCommand;
import seedu.hms.logic.commands.BookingCommand;
import seedu.hms.logic.commands.Command;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.CustomerCommand;
import seedu.hms.logic.commands.ReservationCommand;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.HotelManagementSystemParser;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BillModel;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    //booking and reservation tab index
    private static final SimpleObjectProperty<Integer> selectedPanelOneTabIndex = new SimpleObjectProperty<>();
    //service type and room type tab index
    private static final SimpleObjectProperty<Integer> selectedPanelTwoTabIndex = new SimpleObjectProperty<>();
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final CustomerModel customerModel;
    private final BookingModel bookingModel;
    private final BillModel billModel;
    private final ReservationModel reservationModel;
    private final Storage storage;
    private final CommandHistory history;
    private final HotelManagementSystemParser hotelManagementSystemParser;
    private boolean hotelManagementSystemModified;


    public LogicManager(CustomerModel customerModel, BookingModel bookingModel, ReservationModel reservationModel,
                        BillModel billModel, Storage storage) {
        this.customerModel = customerModel;
        this.bookingModel = bookingModel;
        this.billModel = billModel;
        this.reservationModel = reservationModel;
        this.storage = storage;
        history = new CommandHistory();
        hotelManagementSystemParser = new HotelManagementSystemParser();

        // Set hotelManagementSystemModified to true whenever the models' hms book is modified.
        customerModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        bookingModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        reservationModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
        billModel.getHotelManagementSystem().addListener(observable -> hotelManagementSystemModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        hotelManagementSystemModified = false;

        CommandResult commandResult;
        try {
            Command command = hotelManagementSystemParser.parseCommand(commandText, customerModel, bookingModel,
                reservationModel, billModel);
            if (command instanceof CustomerCommand) {
                commandResult = command.execute(customerModel, history);
            } else if (command instanceof ReservationCommand) {
                commandResult = command.execute(reservationModel, history);
                setSelectedPanelOneTabIndex(1);
                setSelectedPanelTwoTabIndex(1);
            } else if (command instanceof BookingCommand) {
                commandResult = command.execute(bookingModel, history);
                setSelectedPanelOneTabIndex(0);
                setSelectedPanelTwoTabIndex(0);
            } else if (command instanceof BillCommand) {
                commandResult = command.execute(billModel, history);
                setSelectedPanelOneTabIndex(2);
            } else {
                commandResult = command.execute(billModel, history); //maybe it's better to input an empty model?
            }

        } finally {
            history.add(commandText);
        }

        if (hotelManagementSystemModified) {
            logger.info("hms book modified, saving to file.");
            try {
                storage.saveHotelManagementSystem(bookingModel.getHotelManagementSystem());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
        return customerModel.getHotelManagementSystem();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return customerModel.getFilteredCustomerList();
    }

    @Override
    public ObservableList<Booking> getFilteredBookingList() {
        return bookingModel.getFilteredBookingList();
    }

    @Override
    public ObservableList<ServiceType> getServiceTypeList() {
        return bookingModel.getServiceTypeList();
    }

    @Override
    public ObservableList<RoomType> getRoomTypeList() {
        return reservationModel.getRoomTypeList();
    }

    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return reservationModel.getFilteredReservationList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getHotelManagementSystemFilePath() {
        return customerModel.getHotelManagementSystemFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return customerModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        customerModel.setGuiSettings(guiSettings);
    }

    /**
     * Selected tab index in the ServiceTypeAndRoomTypePanel.
     * null if no index is selected.
     * panel one is booking and reservation panel
     */
    public static ReadOnlyProperty<Integer> selectedPanelOneTabIndexProperty() {
        return selectedPanelOneTabIndex;
    }

    public static void setSelectedPanelOneTabIndex(Integer selectedPanelOneTabIndexNew) {
        selectedPanelOneTabIndex.set(selectedPanelOneTabIndexNew);
    }

    /**
     * Selected tab index in the ServiceTypeAndRoomTypePanel.
     * null if no index is selected.
     * panel two is service type and room type panel
     */
    public static ReadOnlyProperty<Integer> selectedPanelTwoTabIndexProperty() {
        return selectedPanelTwoTabIndex;
    }

    public static void setSelectedPanelTwoTabIndex(Integer selectedPanelTwoTabIndexNew) {
        selectedPanelTwoTabIndex.set(selectedPanelTwoTabIndexNew);
    }

    @Override
    public ReadOnlyProperty<Customer> selectedCustomerProperty() {
        return customerModel.selectedCustomerProperty();
    }

    @Override
    public ReadOnlyProperty<Booking> selectedBookingProperty() {
        return bookingModel.selectedBookingProperty();
    }

    @Override
    public ReadOnlyProperty<ServiceType> selectedServiceTypeProperty() {
        return bookingModel.selectedServiceTypeProperty();
    }

    @Override
    public ReadOnlyProperty<Reservation> selectedReservationProperty() {
        return reservationModel.selectedReservationProperty();
    }

    @Override
    public ReadOnlyProperty<RoomType> selectedRoomTypeProperty() {
        return reservationModel.selectedRoomTypeProperty();
    }

    @Override
    public void setSelectedCustomer(Customer customer) {
        customerModel.setSelectedCustomer(customer);
    }

    @Override
    public void setSelectedBooking(Booking booking) {
        bookingModel.setSelectedBooking(booking);
    }

    @Override
    public void setSelectedServiceType(ServiceType serviceType) {
        bookingModel.setSelectedServiceType(serviceType);
    }

    @Override
    public void setSelectedReservation(Reservation reservation) {
        reservationModel.setSelectedReservation(reservation);
    }

    @Override
    public void setSelectedRoomType(RoomType roomType) {
        reservationModel.setSelectedRoomType(roomType);
    }

    @Override
    public BillModel getBillModel() {
        return this.billModel;
    }
}
