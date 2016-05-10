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

    public MultipleChoice(){}

    public MultipleChoice(Set<String> correctChoices,
                          Set<String> wrongChoices,
                          @NotNull String description,
                          Set<TopicBloomLevel> topicBloomLevels) {
        this.setCorrectChoices(correctChoices);
        this.setWrongChoices(wrongChoices);
        this.setDescription(description);
        this.setTopicBloomLevel(topicBloomLevels);
    }

    public Set<String> getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(Set<String> correctChoices) {
        this.correctChoices = correctChoices;
    }

    public Set<String> getWrongChoices() {
        return wrongChoices;
    }

    public void setWrongChoices(Set<String> wrongChoices) {
        this.wrongChoices = wrongChoices;
    }
}
