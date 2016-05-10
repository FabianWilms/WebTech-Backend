package edu.hm.webtech.entities;

import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.utils.NotCompletelyEmpty;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Single Choice represents a Single Choice question. It contains one correct and a set of wrong choices.
 */
@Entity
@NotCompletelyEmpty(fields={"wrongChoices"})
public class SingleChoice extends Exercise {

    @NotEmpty
    private String correctChoice;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> wrongChoices;

    public SingleChoice() {}

    public SingleChoice(final String correctChoice, final Set<String> wrongChoices, final @NotNull String description, final Set<TopicBloomLevel> topicBloomLevels) {
        this.setCorrectChoice(correctChoice);
        this.setWrongChoices(wrongChoices);
        this.setDescription(description);
        this.setTopicBloomLevel(topicBloomLevels);
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public Set<String> getWrongChoices() {
        return wrongChoices;
    }

    public void setWrongChoices(Set<String> wrongChoices) {
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

