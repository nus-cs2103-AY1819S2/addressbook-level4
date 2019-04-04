package seedu.finance.model.budget;
//@@author Jackimaru96

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import seedu.finance.model.category.Category;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.record.Record;

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

    public void set(TotalBudget budget) {
        set(budget.getTotalBudget(), budget.getCurrentBudget());
        this.categoryBudgets = new HashSet<>(budget.getCategoryBudgets());
    }

    public HashSet<CategoryBudget> getCategoryBudgets() {
        return categoryBudgets;
    }

    //========================= Category Budgets ==========================//
    /**
     * Attempts to add a new category budget.
     * Total sum of all category budgets should not exceed totalBudget cap
     * @param budget a CategoryBudget to be added to the list of CategoryBudgets
     * @throws CategoryBudgetExceedTotalBudgetException if adding the categoryBudget will cause
     *         total sum of CategoryBudgets to exceed the total budget of Finance Tracker
     */
    public void setNewCategoryBudget(CategoryBudget budget) throws CategoryBudgetExceedTotalBudgetException {
        double sumOfCategoryBudgets = this.categoryBudgets.stream()
                .mapToDouble(categoryBudget -> categoryBudget.getTotalBudget()).sum();
        Double newTotalCategoryBudget = sumOfCategoryBudgets + budget.getTotalBudget();

        if (newTotalCategoryBudget > this.getTotalBudget()) {
            throw new CategoryBudgetExceedTotalBudgetException(budget, this);
        }


        CategoryBudget catBudgetToAdd = budget;
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
            categoryBudgets.remove(budget);
            categoryBudgets.add(catBudgetToAdd);
            return;
        }

        categoryBudgets.add(budget);
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
