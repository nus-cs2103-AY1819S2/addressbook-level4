package seedu.address.model.pdf;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.testutil.TypicalPdfs.G_PDF;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.TypicalPdfs.ALICE;
//import static seedu.address.testutil.TypicalPdfs.BOB;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//import seedu.address.model.pdf.exceptions.DuplicatePdfException;
//import seedu.address.model.pdf.exceptions.PdfNotFoundException;
//import seedu.address.testutil.PdfBuilder;

public class UniquePdfListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePdfList uniquePdfList = new UniquePdfList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.contains(null);
    }
    /*
    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePdfList.contains(G_PDF));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePdfList.add(ALICE);
        assertTrue(uniquePdfList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePdfList.add(ALICE);
        Pdf editedAlice = new PdfBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePdfList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePdfList.add(ALICE);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        uniquePdfList.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePdfList.add(ALICE);
        uniquePdfList.setPerson(ALICE, ALICE);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(ALICE);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePdfList.add(ALICE);
        Pdf editedAlice = new PdfBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePdfList.setPerson(ALICE, editedAlice);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(editedAlice);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePdfList.add(ALICE);
        uniquePdfList.setPerson(ALICE, BOB);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(BOB);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePdfList.add(ALICE);
        uniquePdfList.add(BOB);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        uniquePdfList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePdfList.add(ALICE);
        uniquePdfList.remove(ALICE);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdfs((UniquePdfList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePdfList.add(ALICE);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(BOB);
        uniquePdfList.setPdfs(expectedUniquePdfList);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdfs((List<Pdf>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePdfList.add(ALICE);
        List<Pdf> pdfList = Collections.singletonList(BOB);
        uniquePdfList.setPdfs(pdfList);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(BOB);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Pdf> listWithDuplicatePdfs = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.setPdfs(listWithDuplicatePdfs);
    }*/

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePdfList.asUnmodifiableObservableList().remove(0);
    }
}
