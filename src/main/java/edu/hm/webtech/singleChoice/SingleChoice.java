package edu.hm.webtech.singleChoice;

import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.exercise.Exercise;
import edu.hm.webtech.utils.NotCompletelyEmpty;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * {@link SingleChoice} represents a single choice question. It contains one correct and a set of wrong choices.
 */
@Entity
@NotCompletelyEmpty(fields={"wrongChoices"})
public class SingleChoice extends Exercise {

    @NotEmpty
    private String correctChoice;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> wrongChoices;

    /**
     * Create a new {@link SingleChoice}. This constructor should not be used. It's purpose is solely for JPA.
     */
    public SingleChoice() {
        //JPA only
    }

    /**
     * Create a new {@link SingleChoice}.
     * 
     * @param correctChoice the correct choice of the exercise
     * @param wrongChoices the wrong choices of the exercise
     * @param description the description of the exercise
     * @param topicBloomLevels the TopicBloomLevel combination of the exercise
     */
    public SingleChoice(final String correctChoice, final Set<String> wrongChoices, final @NotNull String description, final Set<TopicBloomLevel> topicBloomLevels) {
        this.setCorrectChoice(correctChoice);
        this.setWrongChoices(wrongChoices);
        this.setDescription(description);
        this.setTopicBloomLevel(topicBloomLevels);
    }

    /**
     * Gets the correct choice of the exercise.
     * 
     * @return the correct choice of the exercise
     */
    public String getCorrectChoice() {
        return correctChoice;
    }

    /**
     * Sets the correct choice of the exercise.
     * 
     * @param correctChoice the correct choice of the exercise
     */
    public void setCorrectChoice(final String correctChoice) {
        this.correctChoice = correctChoice;
    }

    /**
     * Gets the wrong choices of the exercise.
     * 
     * @return the wrong choices of the exercise
     */
    public Set<String> getWrongChoices() {
        return wrongChoices;
    }

    /**
     * Sets the wrong choices of the exercise.
     * 
     * @param wrongChoices the wrong choices of the exercise
     */
    public void setWrongChoices(final Set<String> wrongChoices) {
        this.wrongChoices = wrongChoices;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.correctChoice);
        hash = 59 * hash + Objects.hashCode(this.wrongChoices);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SingleChoice other = (SingleChoice) obj;
        if (!Objects.equals(this.correctChoice, other.correctChoice)) {
            return false;
        }
        if (!Objects.equals(this.wrongChoices, other.wrongChoices)) {
            return false;
        }
        return true;
    }
}

