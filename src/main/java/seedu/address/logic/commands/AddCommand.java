package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTALPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLINGPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_BUYER = "Parameters for buyer: " + PREFIX_CUSTOMER + "CUSTOMER_TYPE "
            + PREFIX_NAME + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_REMARK + "REMARK \n"
            + "Example for buyer: " + COMMAND_WORD + " " + PREFIX_CUSTOMER + "buyer " + PREFIX_NAME + "Ian Berry "
            + PREFIX_PHONE + "96254182 " + PREFIX_EMAIL + "ianberry@example.com " + PREFIX_REMARK + "I am a buyer\n";

    public static final String MESSAGE_USAGE_SELLER = "Parameters for seller: " + PREFIX_CUSTOMER + "CUSTOMER_TYPE "
            + PREFIX_NAME + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_REMARK + "REMARK "
            + PREFIX_ADDRESS + "ADDRESS " + PREFIX_SELLINGPRICE + "SELLING_PRICE " + "[" + PREFIX_TAG + "TAG" + "]...\n"
            + "Example for seller: " + COMMAND_WORD + " " + PREFIX_CUSTOMER + "seller " + PREFIX_NAME + "Amanda Brown "
            + PREFIX_PHONE + "92615231 " + PREFIX_EMAIL + "amandabrown@example.com " + PREFIX_REMARK + "I am a seller "
            + PREFIX_ADDRESS + "Blk 288D Jurong East Street 77, #13-38 " + PREFIX_SELLINGPRICE + "500000 "
            + PREFIX_TAG + "MRT\n";

    public static final String MESSAGE_USAGE_LANDLORD = "Parameters for landlord: " + PREFIX_CUSTOMER + "CUSTOMER_TYPE "
            + PREFIX_NAME + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_REMARK + "REMARK "
            + PREFIX_ADDRESS + "ADDRESS " + PREFIX_RENTALPRICE + "RENTAL_PRICE " + "[" + PREFIX_TAG + "TAG" + "]...\n"
            + "Example for landlord: " + COMMAND_WORD + " " + PREFIX_CUSTOMER + "landlord " + PREFIX_NAME
            + "Matt Thomson " + PREFIX_PHONE + "81524521 " + PREFIX_EMAIL + "johnd@example.com " + PREFIX_REMARK
            + "I am a landlord " + PREFIX_ADDRESS + "Blk 131 Geylang East Ave 1, #06-40 " + PREFIX_RENTALPRICE + "2500 "
            + PREFIX_TAG + "MRT \n";

    public static final String MESSAGE_USAGE_TENANT = "Parameters for tenant: " + PREFIX_CUSTOMER + "CUSTOMER_TYPE "
            + PREFIX_NAME + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_REMARK + "REMARK \n"
            + "Example for tenant: " + COMMAND_WORD + " " + PREFIX_CUSTOMER + "tenant " + PREFIX_NAME + "Evan Bell "
            + PREFIX_PHONE + "91756212 " + PREFIX_EMAIL + "evanbell@example.com " + PREFIX_REMARK + "I am a tenant\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + MESSAGE_USAGE_BUYER + MESSAGE_USAGE_SELLER + MESSAGE_USAGE_LANDLORD + MESSAGE_USAGE_TENANT;

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PERSON_ARCHIVE = "This person already exists in the archive book";
    public static final String MESSAGE_DUPLICATE_PERSON_PIN = "This person already exists in the pin book";
    public static final String MESSAGE_DUPLICATE_IDENTITY_FIELD = "Customer with one or more duplicate identity "
            + "field found in address book";
    public static final String MESSAGE_DUPLICATE_IDENTITY_FIELD_ARCHIVE = "Customer with one or more duplicate "
            + "identity field found in archive book";
    public static final String MESSAGE_DUPLICATE_IDENTITY_FIELD_PIN = "Customer with one or more duplicate "
            + "identity field found in pin book";



    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (model.hasPersonArchive(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_ARCHIVE);
        } else if (model.hasPersonPin(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_PIN);
        } else if (model.hasSameIdentityField(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD);
        } else if (model.hasSameIdentityFieldArchive(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD_ARCHIVE);
        } else if (model.hasSameIdentityFieldPin(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD_PIN);
        }

        model.addPerson(toAdd);
        model.commitBooks();
        model.setSelectedPinPerson(null);
        model.setSelectedPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean requiresMainList() {
        return true;
    }

    @Override
    public boolean requiresArchiveList() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
