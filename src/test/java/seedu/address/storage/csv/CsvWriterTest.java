package seedu.address.storage.csv;

import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CsvWriterTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());

    @Test
    public void convertStringToCsvFieldCompliantFormat_inputWithComma_correctCsvFieldCompliantFormat() {
        String stringToTest = "Otsuka, Pharmaceutical Co.";
        String expectedString = "\"Otsuka, Pharmaceutical Co.\"";
        Assert.assertEquals(expectedString, new CsvWriter("example", model)
                .convertStringToCsvFieldCompliantFormat(stringToTest));
    }

    @Test
    public void convertStringToCsvFieldCompliantFormat_inputWithDoubleQuotes_correctCsvFieldCompliantFormat() {
        String stringToTest = "\"Otsuka Pharmaceutical Co.\"";
        String expectedString = "\"\"Otsuka Pharmaceutical Co.\"\"";
        Assert.assertEquals(expectedString, new CsvWriter("example", model)
                .convertStringToCsvFieldCompliantFormat(stringToTest));
    }

    @Test
    public void convertStringToCsvFieldCompliantFormat_inputWithCommaAndDoubleQuotes_correctCsvFieldCompliantFormat() {
        String stringToTest = "\"Otsuka, Pharmaceutical Co.\"";
        String expectedString = "\"\"\"Otsuka, Pharmaceutical Co.\"\"\"";
        Assert.assertEquals(expectedString, new CsvWriter("example", model)
                .convertStringToCsvFieldCompliantFormat(stringToTest));
    }

    @Test
    public void convertStringToCsvFieldCompliantFormat_inputWithLineBreak_correctCsvFieldCompliantFormat() {
        String stringToTest = "\"Otsuka \n Pharmaceutical Co.\"";
        String expectedString = "\"\"\"Otsuka \n Pharmaceutical Co.\"\"\"";
        Assert.assertEquals(expectedString, new CsvWriter("example", model)
                .convertStringToCsvFieldCompliantFormat(stringToTest));
    }
}
