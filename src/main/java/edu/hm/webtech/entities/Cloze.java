package edu.hm.webtech.entities;

import edu.hm.webtech.TopicBloomLevel;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cloze with ordered and unordered solution lists. Solutions are labeled with "<<<" and ">>>".
 *
 * @author M. Streich
 * @version 02.05.2016
 */
@Entity
public class Cloze extends Exercise {
    private static final String REGEX_OMISSION = "[^/]<<<(.*?[^/])>>>";
    private static final Pattern omitPattern = Pattern.compile(REGEX_OMISSION);
    /**
     * Text with labeled solutions. != description!!!
     */
    private String text;
    /**
     * Text with omissions.
     */
    private String textWithOmissions;
    /**
     * Ordered solutions.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> omissionsList = new ArrayList<>();

    public Cloze() {
    }

    public Cloze(final String description, final String text, final Set<TopicBloomLevel> topicBloomLevels) {
        this.setDescription(description);
        this.setText(text);
        this.setTopicBloomLevel(topicBloomLevels);
    }

    public String getText() {
        return text;
    }

    /**
     * Ordered solutions.
     *
     * @return Ordered solutions.
     */
    public List<String> getOmissionsList() {
        return omissionsList;
    }

    /**
     * Text with omissions. Omissions labeled with "<<<>>>".
     *
     * @return Text mit Luecken.
     */
    public String getTextWithOmissions() {
        return textWithOmissions;
    }

    /**
     * Setter text. Solutions are to label with "<<<" and ">>>".
     *
     * @param text Text with labels.
     */
    public void setText(final String text) {
        this.text = text;
        final Matcher matcher = omitPattern.matcher(text);
        while (matcher.find()) {
            this.omissionsList.add(matcher.group(1));
        }
        this.textWithOmissions = text.replaceAll(REGEX_OMISSION, "<<<>>>");
    }
}
