package pkg_entities;
import java.util.HashMap;

/**
 * Stores a private collection of trainers keyed by name.
 *
 * The internal collection is fully encapsulated and cannot be manipulated
 * directly
 *
 * from outside this class.
 *
 * @author Barnabe Jouanard
 * @version 2026.04.13
 */
public class TrainerList {
    /** Internal storage keyed by trainer name. */
    private HashMap<String, Trainer> aTrainers;

    /**
     * Creates an empty trainer list.
     */
    public TrainerList() {
        this.aTrainers = new HashMap<String, Trainer>();
    } // TrainerList()

    /**
     * Adds or replaces a trainer under the given key.
     *
     * @param pName short identifier used for later retrieval
     * @param pTrainer the {@link Trainer} to store
     */
    public void addTrainer(final String pName, final Trainer pTrainer) {
        this.aTrainers.put(pName, pTrainer);
    }

    /**
     * Removes the trainer associated with the given key.
     *
     * @param pName the lookup key
     */
    public void removeTrainer(final String pName) {
        this.aTrainers.remove(pName);
    }

    public HashMap<String, Trainer> getAllTrainers() {
        return this.aTrainers;
    }

    /**
     * Checks whether this trainer list contains no elements.
     *
     * @return {@code true} when no trainers are stored
     */
    public boolean isEmpty() {
        return this.aTrainers.isEmpty();
    }

    /**
     * Builds a multi-line string with each trainer's description,
     * or a default notice when the list is empty.
     *
     * @return formatted trainer descriptions
     */
    public String getTrainersDescription() {
        if (this.aTrainers.isEmpty()) {
            return "There are no trainers here!";
        }
        String vResult = "";
        for (String vName : this.aTrainers.keySet()) {
            vResult += "There is " + vName + ", " + this.aTrainers.get(vName).getTrainerDescription() + "\n";
        }
        return vResult;
    }
} // TrainerList
