package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.restaurant.categories.Categories;

public class CategoriesAutoCompleteTextField extends TextField {

    @FXML
    private ContextMenu suggestions;

    public CategoriesAutoCompleteTextField() {
        super();
        suggestions = new ContextMenu();
        textProperty().addListener((unused1, unused2, unused3) -> showAutoComplete());
    }

    /**
     * Shows the autocomplete context menu if conditions are satisfied.
     */
    private void showAutoComplete() {
        TextField current = CategoriesAutoCompleteTextField.this;

        Optional<Prefix> recentPrefixOptional = getRecentlyEnteredPrefix(getText());
        if (recentPrefixOptional.isPresent()) {
            List<String> retrievedSuggestions = Categories.getCategoriesSuggestions(recentPrefixOptional.get());
            List<MenuItem> menuItemsToBeAdded = convertSuggestionsToMenuItems(retrievedSuggestions);
            suggestions.getItems().clear();
            suggestions.getItems().addAll(menuItemsToBeAdded);
            suggestions.show(CategoriesAutoCompleteTextField.this, Side.BOTTOM, current.getInputMethodRequests().getTextLocation(0).getX(), 0);
        } else {
            suggestions.hide();
        }
    }

    /**
     * Converts a list of suggestions to menu items.
     * Defines the actions on each of the menu items as well.
     */
    private List<MenuItem> convertSuggestionsToMenuItems(List<String> retrievedSuggestions) {
        return retrievedSuggestions.stream()
                .map(result -> {
                    MenuItem resultMenuItem = new MenuItem(result);
                    resultMenuItem.setOnAction((event) -> {
                        setText(trimInput(getText()) + result);
                        CategoriesAutoCompleteTextField.this.positionCaret(getText().length());
                        suggestions.hide();
                    });
                    return resultMenuItem;
                }).collect(Collectors.toList());
    }

    /**
     * Trims the input to remove half entered inputs after the last prefix.
     */
    private String trimInput(String input) {
        requireNonNull(input);
        assert(getRecentlyEnteredPrefix(input).isPresent());
        String[] inputTokens = input.split(" ");
        String lastWord = inputTokens[inputTokens.length - 1];
        String trimmedLastWord = lastWord.split("/")[0] + "/";
        inputTokens[inputTokens.length - 1] = trimmedLastWord;
        String result = "";
        for (String token : inputTokens) {
            result = result.concat(token);
            result = result.concat(" ");
        }
        return result.trim();
    }

    /**
     * Get the most recently entered prefix from the input, if any.
     */
    private Optional<Prefix> getRecentlyEnteredPrefix(String input) {
        requireNonNull(input);
        String[] inputTokens = input.split(" ");
        if (inputTokens.length < 1) {
            return Optional.empty();
        } else {
            String lastWord = inputTokens[inputTokens.length - 1];
            if (lastWord.matches("[a-z]*/[A-Za-z]*")) {
                String prefix = lastWord.split("/")[0] + "/";
                return Optional.of(new Prefix(prefix));
            }
            return Optional.empty();
        }
    }
}
