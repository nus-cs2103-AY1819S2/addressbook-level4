package seedu.address.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This is a custom UI control that inherits from the TextField class,
 * with auto-complete functionality. It aims to show users the type of
 * commands they can type in the command text field.
 * @author Hui Chun
 */
public class AutoCompleteTextField extends TextField {

    public static final String ERROR_STYLE_CLASS = "error";

    private final SortedSet<String> suggestions;

    private ContextMenu autocompleteBox;

    public AutoCompleteTextField() {
        super();
        suggestions = new TreeSet<>();
        autocompleteBox = new ContextMenu();

        textProperty().addListener((observable, s1, s2) -> {

            // calls #setStyleToDefault() whenever there is a change to the text of the command box.
            setStyleToDefault();

            if (getText().length() != 0 && suggestions.size() > 0) {
                Iterator iterator = suggestions.iterator();
                LinkedList<String> searchResult = new LinkedList<>();
                while (iterator.hasNext()) {
                    String suggestion = iterator.next().toString();
                    if (suggestion.startsWith(getText())) {
                        searchResult.add(suggestion);
                    }
                }

                buildContextMenu(searchResult);
                if (!autocompleteBox.isShowing()) {
                    autocompleteBox.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                }
            } else {
                autocompleteBox.hide();
            }
        });

    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        this.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Get the existing set of autocomplete suggestions.
     * @return The existing autocomplete suggestions.
     */
    public SortedSet<String> getSuggestions() {
        return this.suggestions;
    }

    /**
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void buildContextMenu(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();

        // Only show max 10 entries in the autocompletion box
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label label = new Label(result);
            CustomMenuItem item = new CustomMenuItem(label, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    positionCaret(result.length());
                    autocompleteBox.hide();
                }
            });
            menuItems.add(item);
        }
        autocompleteBox.getItems().clear();
        autocompleteBox.getItems().addAll(menuItems);
    }
}
