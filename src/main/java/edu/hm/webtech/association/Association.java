package edu.hm.webtech.association;

import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.exercise.Exercise;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides a {@link Exercise} type that asks to connect multiple unique Strings to one or multiple matching ones.
 *
 * @author peter-mueller
 */
@Entity
public class Association extends Exercise {

    @ElementCollection(targetClass = HashSet.class)
    @NotEmpty
    /** The Map of all keys connected to their matching associations. */
    private Map<String, Set<String>> associations = new HashMap<>();

    /**
     * Create a new {@link Association}.
     *
     * @param description     The description for this Exercise.
     * @param topicBloomLevel The Topic and Bloom Level of this Exercise.
     * @param associations    The map for the associations. The String Key will match to a Set of
     *                        its fitting Associations.
     */
    public Association(final String description, final Set<TopicBloomLevel> topicBloomLevel, final Map<String, Set<String>> associations) {
        super();
        setDescription(description);
        setTopicBloomLevel(topicBloomLevel);
        this.associations = associations;
    }

    /**
     * Create a new {@link Association}. This constructor should not be used. It's purpose is solely for JPA.
     */
    Association() {
        //JPA only
    }

    /**
     * Get the String Associations for this Exercise.
     * @return The Associations.
     */
    public Map<String, Set<String>> getAssociations() {
        return associations;
    }

    /**
     * Set the Associations.
     * @param associations A map with a key describing the Association to a Set of his correct Values.
     */
    public void setAssociations(final Map<String, Set<String>> associations) {
        this.associations = associations;
    }
}
