package seedu.hms.model.bill;

import seedu.hms.model.customer.Customer;

/**
 * Represents a Customer's bill in the system.
 */
public class Bill {

    private Customer customer;
    private double amountPaid;
    private double amountReservation;
    private double amountBooking;

    /**
     * Constructs a {@code Bill}.
     *
     * @param customer   Customer details.
     * @param amountPaid Amount already paid by customers.
     */

    public Bill(Customer customer, double amountPaid, double amountReservation, double amountBooking) {

        this.customer = customer;
        this.amountPaid = amountPaid;
        this.amountBooking = amountBooking;
        this.amountReservation = amountReservation;

    }

    /**
     * Returns the customer whose bill is to be calculated.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns the amount already paid by the customer.
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Returns the amount customer has to pay for reservation.
     */
    public double getAmountReservation() {
        return amountReservation;
    }

    /**
     * Returns the amount customer has to pay for booking.
     */
    public double getAmountBooking() {
        return amountBooking;
    }


    @Override
    public String toString() {
        return " Amount already paid " + amountPaid
            + " by " + customer.getName();
    }
}
