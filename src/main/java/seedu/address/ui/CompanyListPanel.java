package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Company;

/**
 * Panel containing the list of companies.
 */
public class CompanyListPanel extends UiPart<Region> {
    private static final String FXML = "CompanyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CompanyListPanel.class);

    @FXML
    private ListView<Company> companyListView;

    public CompanyListPanel(ObservableList<Company> companyList, ObservableValue<Company> selectedCompany,
                           Consumer<Company> onSelectedCompanyChange) {
        super(FXML);
        companyListView.setItems(companyList);
        companyListView.setCellFactory(listView -> new CompanyListViewCell());
        companyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in company list panel changed to : '" + newValue + "'");
            onSelectedCompanyChange.accept(newValue);
        });
        selectedCompany.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected company changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected company,
            // otherwise we would have an infinite loop.
            if (Objects.equals(companyListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                companyListView.getSelectionModel().clearSelection();
            } else {
                int index = companyListView.getItems().indexOf(newValue);
                companyListView.scrollTo(index);
                companyListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Company} using a {@code CompanyCard}.
     */
    class CompanyListViewCell extends ListCell<Company> {
        @Override
        protected void updateItem(Company company, boolean empty) {
            super.updateItem(company, empty);

            if (empty || company == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CompanyCard(company, getIndex() + 1).getRoot());
            }
        }
    }

}
