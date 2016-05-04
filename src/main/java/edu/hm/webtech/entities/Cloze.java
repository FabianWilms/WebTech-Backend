package edu.hm.webtech.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cloze with ordered and unordered solution lists. Solutions are labeled with "<<<" und ">>>".
 *
 * @author M. Streich
 * @version 02.05.2016
 */
public class Cloze extends Exercise {
    private static final String REGEX_OMISSION = "[^/]<<<(.*?)[^/]>>>";
    private static final Pattern omitPattern = Pattern.compile(REGEX_OMISSION);
    /**
     * Text with labeled solutions.
     */
    private String text;
    /**
     * Text with omissions.
     */
    private String textWithOmissions;
    /**
     * Ordered solutions.
     */
    private List<String> omissionsList = new ArrayList<>();

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
