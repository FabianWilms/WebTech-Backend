package edu.hm.webtech.cloze;

import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.exercise.Exercise;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Cloze} with ordered and unordered solution lists. Solutions are labeled with "<<<" and ">>>".
 *
 * @author M. Streich
 * @version 02.05.2016
 */
@Entity
public class Cloze extends Exercise {

    /**
     * Text with labeled solutions. != description!!!
     */
    @NotEmpty
    private String text;

    /**
     * Create a new {@link Cloze}. This constructor should not be used. It's purpose is solely for JPA.
     */
    public Cloze() {
        //JPA only
    }

    /**
     * Create a new {@link Cloze}.
     * 
     * @param description The description for this Exercise
     * @param text text with labeled solutions
     * @param topicBloomLevels The map for the associations. The String Key will match to a Set of
     *                        its fitting Associations.
     */
    public Cloze(final String description, final String text, final List<TopicBloomLevel> topicBloomLevels) {
        this.setDescription(description);
        this.setText(text);
        this.setTopicBloomLevel(topicBloomLevels);
    }

    /**
     * Get the text with labeled solutions.
     * 
     * @return the text with labeled solutions
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

