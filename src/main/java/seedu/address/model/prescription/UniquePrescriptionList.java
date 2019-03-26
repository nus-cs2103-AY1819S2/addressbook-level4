package seedu.address.model.prescription;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.prescription.exceptions.DuplicatePrescriptionException;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;


public class UniquePrescriptionList implements Iterable<Prescription>{
    private final ObservableList<Prescription> internalList = FXCollections.observableArrayList();


    /**
     * Add a new prescription to the ArrayList.
     * The new prescription to add must not exist in the current ArrayList.
     */
    public void addPrescription(Prescription toAdd){
        requireNonNull(toAdd);
        if (contains(toAdd)){
            throw new DuplicatePrescriptionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if the ArrayList contains an equivalent prescription as the input.
     */
    public boolean contains(Prescription other){
        requireNonNull(other);
        return internalList.stream().anyMatch(other::equals);
    }
    public void remove(Prescription toRemove){
        requireNonNull(toRemove);
        boolean result = internalList.remove(toRemove);
        if (!result){
            throw new PrescriptionNotFoundException();
        }
    }

    @Override
    public Iterator<Prescription> iterator(){
        return internalList.iterator();
    }

    @Override
    public int hashCode(){
        return internalList.hashCode();
    }

}

