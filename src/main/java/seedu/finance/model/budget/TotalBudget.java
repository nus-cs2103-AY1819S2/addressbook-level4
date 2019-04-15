package seedu.finance.model.budget;
//@@author Jackimaru96

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.finance.model.category.Category;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.exceptions.SpendingInCategoryBudgetExceededException;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.UniqueRecordList;

/**
 * Represents the TotalBudget of the FinanceTracker
 */
public class TotalBudget extends Budget {
    private HashSet<CategoryBudget> categoryBudgets;

    public TotalBudget() {
        super();
        this.categoryBudgets = new HashSet<>();
    }

    public TotalBudget(Double budget) {
        super(budget);
        this.categoryBudgets = new HashSet<>();
    }

    public TotalBudget(Double totalBudget, double currentBudget) {
        super(totalBudget, currentBudget);
        this.categoryBudgets = new HashSet<>();
    }

    public void set(TotalBudget budget) {
        if (budget.getTotalBudget() == 0) {
            set(0, 0);
            setCategoryBudgets(new HashSet<>());
        }
        set(budget.getTotalBudget(), budget.getCurrentBudget());
        this.categoryBudgets.clear();
        this.categoryBudgets.addAll(
                budget.getCategoryBudgets()
                .stream()
                .map(CategoryBudget::new)
                .collect(Collectors.toSet())
        );
    }

    public void setCategoryBudgets(HashSet<CategoryBudget> categoryBudgets) {
        this.categoryBudgets = categoryBudgets;
    }

    public HashSet<CategoryBudget> getCategoryBudgets() {
        return categoryBudgets;
    }

    /**
     * Updates the budget of TotalBudget based on the budget given and current list of records.
     * Throws an exception if the new budget to be set is less than the total amount allocated
     * to the different budget categories.
     *
     * @param budget the budget to be set
     * @param records the records in FinanceTracker to check spending and update budget
     * @throws CategoryBudgetExceedTotalBudgetException thrown when the total amount allocated
     * in the Set of {@code CategoryBudget} is more than budget to be set
     */
    public void updateBudget(Budget budget, ObservableList<Record> records) throws
            CategoryBudgetExceedTotalBudgetException {
        Double totalCategoryBudget = 0.0;
        for (CategoryBudget cb: this.categoryBudgets) {
            totalCategoryBudget += cb.getTotalBudget();
        }
        if (budget.getTotalBudget() < totalCategoryBudget) {
            throw new CategoryBudgetExceedTotalBudgetException(budget, totalCategoryBudget);
        }
        set(budget.getTotalBudget(), budget.getCurrentBudget());
        updateBudget(records);
    }

    //========================= Category Budgets ==========================//
    /**
     * Attempts to add a new category budget.
     * Total sum of all category budgets should not exceed totalBudget cap
     * @param budget a CategoryBudget to be added to the list of CategoryBudgets
     * @throws CategoryBudgetExceedTotalBudgetException if adding the categoryBudget will cause
     *         total sum of CategoryBudgets to exceed the total budget of Finance Tracker
     * @throws SpendingInCategoryBudgetExceededException if the user tries to allocate a budget that is less
     *          than the current spending in the category
     */
    public void setNewCategoryBudget(CategoryBudget budget, UniqueRecordList records) throws
            CategoryBudgetExceedTotalBudgetException,
            SpendingInCategoryBudgetExceededException {
        Category categoryOfBudget = budget.getCategory();
        // Checks if the sum of all categoryBudgets exceed totalBudget
        double sumOfCategoryBudgets = 0.00;
        for (CategoryBudget cb: this.categoryBudgets) {
            if (!cb.getCategory().equals(categoryOfBudget)) {
                sumOfCategoryBudgets += cb.getTotalBudget();
            }
        }

        Double newTotalCategoryBudget = sumOfCategoryBudgets + budget.getTotalBudget();

        if (newTotalCategoryBudget > this.getTotalBudget()) {
            throw new CategoryBudgetExceedTotalBudgetException(budget, this);
        }

        CategoryBudget catBudgetToAdd = budget;
        // Checks to see if user previous allocated budget
        // under this category
        if (categoryBudgets.contains(budget)) {
            CategoryBudget cBudget;
            Iterator<CategoryBudget> it = categoryBudgets.iterator();
            while (it.hasNext()) {
                cBudget = it.next();
                if (cBudget.equals(budget)) {
                    catBudgetToAdd.currentSpendings = cBudget.currentSpendings;
                    catBudgetToAdd.currentBudget = catBudgetToAdd.totalBudget - catBudgetToAdd.currentSpendings;
                }
            }
        }
        ObservableList<Record> recordList = records.asUnmodifiableObservableList();
        for (Record r: recordList) {
            if (r.getCategory().equals(categoryOfBudget)) {
                catBudgetToAdd.currentSpendings += r.getAmount().getValue();
            }
        }
        catBudgetToAdd.currentBudget = catBudgetToAdd.totalBudget - catBudgetToAdd.currentSpendings;

        if (catBudgetToAdd.currentSpendings > catBudgetToAdd.totalBudget) {
            throw new SpendingInCategoryBudgetExceededException(catBudgetToAdd);
        }

        this.categoryBudgets.remove(catBudgetToAdd);
        this.categoryBudgets.add(catBudgetToAdd);
    }

    @Override
    public boolean addRecord(Record r) {
        Double spending = r.getAmount().getValue();
        this.currentSpendings += spending;
        this.currentBudget -= spending;
        AtomicInteger catBudgetNotExceeded = new AtomicInteger(0);
        categoryBudgets.forEach(catBudget -> {
            if (catBudget.getCategory().equals(r.getCategory())) {
                if (!catBudget.addRecord(r)) {
                    catBudgetNotExceeded.getAndIncrement();
                }
            }
        });
        return this.currentSpendings <= this.totalBudget && catBudgetNotExceeded.get() < 1;


    }

    @Override
    public void editRecord(Record target, Record editedRecord) {
        super.editRecord(target, editedRecord);
        for (CategoryBudget cb: this.categoryBudgets) {
            if (cb.getCategory().equals(target.getCategory())) {
                if (target.getCategory().equals(editedRecord.getCategory())) {
                    cb.editRecord(target, editedRecord);
                } else {
                    cb.removeRecord(target);
                }
            } else if (cb.getCategory().equals(editedRecord.getCategory())) {
                cb.addRecord(editedRecord);
            }
        }
    }

    @Override
    public void removeRecord(Record r) {
        super.removeRecord(r);
        Category c = r.getCategory();
        CategoryBudget deleteThis = null;
        List<CategoryBudget> catBudget = this.categoryBudgets.stream().collect(Collectors.toList());
        for (int i = 0; i < catBudget.size(); i++) {
            if (catBudget.get(i).getCategory().equals(c)) {
                deleteThis = catBudget.get(i);
            }
        }
        if (deleteThis != null) {
            deleteThis.removeRecord(r);
        }
    }

}
