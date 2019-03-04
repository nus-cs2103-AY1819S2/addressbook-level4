package seedu.address.model.bill;

import seedu.address.model.customer.Customer;

/**
 * Represents a Customer's bill in the system.
 */
public class Bill {

    private Customer customer;
    private int amountPaid;

    /**
     * Constructs a {@code Bill}.
     *
     * @param customer Customer details.
     * @param amountPaid Amount already paid by customers.
     */
    public Bill(Customer customer, int amountPaid) {

        this.customer = customer;
        this.amountPaid = amountPaid;

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
    public int getAmountPaid() {
        return amountPaid;
    }

    @Override
    public String toString() {
        return " Amount already paid " + amountPaid
            + " by " + customer;
    }
}
