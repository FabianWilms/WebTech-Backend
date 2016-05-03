package edu.hm.webtech.entities;

import edu.hm.webtech.utils.NotCompletelyEmpty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.Set;

/**
 * Created by Fabian on 29.04.2016.
 */
@Entity
@NotCompletelyEmpty(fields={"correctChoices", "wrongChoices"})
public class MultipleChoice extends Exercise {

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> correctChoices;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> wrongChoices;

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
