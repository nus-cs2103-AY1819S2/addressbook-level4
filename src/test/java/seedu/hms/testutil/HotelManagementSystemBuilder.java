package seedu.hms.testutil;

import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.customer.Customer;

/**
 * A utility class to help with building HotelManagementSystem objects.
 * Example usage: <br>
 * {@code HotelManagementSystem ab = new HotelManagementSystemBuilder().withCustomer("John", "Doe").build();}
 */
public class HotelManagementSystemBuilder {

    private HotelManagementSystem hotelManagementSystem;

    public HotelManagementSystemBuilder() {
        hotelManagementSystem = new HotelManagementSystem();
    }

    public HotelManagementSystemBuilder(HotelManagementSystem hotelManagementSystem) {
        this.hotelManagementSystem = hotelManagementSystem;
    }

    /**
     * Adds a new {@code Customer} to the {@code HotelManagementSystem} that we are building.
     */
    public HotelManagementSystemBuilder withCustomer(Customer customer) {
        hotelManagementSystem.addCustomer(customer);
        return this;
    }

    public HotelManagementSystem build() {
        return hotelManagementSystem;
    }
}
