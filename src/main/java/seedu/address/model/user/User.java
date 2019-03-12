package seedu.address.model.user;

import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.exceptions.CardDataImportException;
import seedu.address.commons.exceptions.DuplicateCardDataException;
import seedu.address.commons.util.CsvUtil;

/**
 * Represents a user data and allows importing and exporting
 */
public class User {
    private String name;
    private List<CardSrsData> cardData; // Contains User-specific data on Cards

    /**
     *  returns true if the function is able to write file
     */
    public boolean exportData(Path filepath, List<String[]> cardData) {
        try {
            return CsvUtil.writeCsvFile(filepath, cardData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException (e);
        }
    }

    /**
     *  attempts to import data
     *  exceptions : Duplicate file, file does not exist
     */
    public CardSrsData importData(Path filepath) throws DuplicateCardDataException, CardDataImportException {
        try {
            CardSrsData importCardData = (CardSrsData) CsvUtil.readCsvFile(filepath);

            if (cardData.contains(importCardData)) {
                throw new CardDataImportException("Error importing file, there exists a duplicate file");
            }

            return importCardData; }

        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<CardSrsData> getCardData() {
        return cardData;
    }
}
