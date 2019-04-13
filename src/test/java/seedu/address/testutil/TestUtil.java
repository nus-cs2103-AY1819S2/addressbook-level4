package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.table.Table;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the order item in the {@code model}'s order item list.
     */
    public static Index getOrderItemsMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredOrderItemList().size() / 2);
    }

    /**
     * Returns the last index of the order item in the {@code model}'s order item list.
     */
    public static Index getOrderItemsLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredOrderItemList().size());
    }

    /**
     * Returns the order item in the {@code model}'s order item list at {@code index}.
     */
    public static OrderItem getOrderItem(Model model, Index index) {
        return model.getFilteredOrderItemList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the menu item in the {@code model}'s menu item list.
     */
    public static Index getMenuItemsMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMenuItemList().size() / 2);
    }

    /**
     * Returns the last index of the menu item in the {@code model}'s menu item list.
     */
    public static Index getMenuItemsLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMenuItemList().size());
    }

    /**
     * Returns the menu item in the {@code model}'s menu item list at {@code index}.
     */
    public static MenuItem getMenuItem(Model model, Index index) {
        return model.getFilteredMenuItemList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the table in the {@code model}'s table list.
     */
    public static Index getTableMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTableList().size() / 2);
    }

    /**
     * Returns the last index of the table in the {@code model}'s table list.
     */
    public static Index getTableLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredTableList().size());
    }

    /**
     * Returns the table in the {@code model}'s table list at {@code index}.
     */
    public static Table getTable(Model model, Index index) {
        return model.getFilteredTableList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the revenue in the {@code model}'s revenue list.
     */
    public static Index getRevenueMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredRevenueList().size() / 2);
    }

    /**
     * Returns the last index of the revenue in the {@code model}'s revenue list.
     */
    public static Index getRevenueLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredRevenueList().size());
    }

    /**
     * Returns the daily revenue in the {@code model}'s revenue list at {@code index}.
     */
    public static Revenue getRevenue(Model model, Index index) {
        return model.getFilteredRevenueList().get(index.getZeroBased());
    }
}
