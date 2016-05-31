package edu.hm.webtech.multipleChoice;

import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.exercise.Exercise;
import edu.hm.webtech.utils.NotCompletelyEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * MultipleChoice represents a Multiple Choice question. It contains a Set of correct and a set of wrong choices.
 * At least one element has to exist in one of the two sets for a valid MultipleChoice-Object.
 */
@Entity
@NotCompletelyEmpty(fields={"correctChoices", "wrongChoices"})
public class MultipleChoice extends Exercise {

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> correctChoices;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> wrongChoices;

    /**
     * Create a new {@link MultipleChoice}. This constructor should not be used. It's purpose is solely for JPA.
     */
    public MultipleChoice(){
        //JPA only
    }

    /**
     * Creates a new {@link MultipleChoice}.
     * 
     * @param correctChoices the correct choices of the exercise
     * @param wrongChoices the wrong choices of the exercise
     * @param description the description of the exercise
     * @param topicBloomLevels the TopicBloomLevels of the exercise
     */
    public MultipleChoice(final Set<String> correctChoices,
                          final Set<String> wrongChoices,
                          final @NotNull String description,
                          final Set<TopicBloomLevel> topicBloomLevels) {
        setCorrectChoices(correctChoices);
        setWrongChoices(wrongChoices);
        setDescription(description);
        setTopicBloomLevel(topicBloomLevels);
    }

    /**
     * Gets the correct choices of the exercise.
     * 
     * @return correct choices of the exercise
     */
    public Set<String> getCorrectChoices() {
        return correctChoices;
    }

    /**
     * Sets the correct choices of exercise.
     * 
     * @param correctChoices correct choices of the exercise
     */
    public void setCorrectChoices(final Set<String> correctChoices) {
        this.correctChoices = correctChoices;
    }

    /**
     * Gets the wrong choices of the exercise.
     * 
     * @return wrong choices of the exercise
     */
    public Set<String> getWrongChoices() {
        return wrongChoices;
    }

    /**
     * Sets the wrong choices of the exercise.
     * 
     * @param wrongChoices wrong choices of the exercise
     */
    public void setWrongChoices(final Set<String> wrongChoices) {
        this.wrongChoices = wrongChoices;
    }
}
