package edu.hm.webtech.entities;

import edu.hm.webtech.TopicBloomLevel;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cloze with ordered and unordered solution lists. Solutions are labeled with "<<<" and ">>>".
 *
 * @author M. Streich
 * @version 02.05.2016
 */
@Entity
public class Cloze extends Exercise {
    /**
     * Labels beginning of an omission.
     */
    private static final String OMISSION_OPEN_SIGN = "<<<";
    /**
     * Labels end of an omission.
     */
    private static final String OMISSION_CLOSE_SIGN = ">>>";
    /**
     * Labels the following character not to use as an omission sign or an escape sign.
     */
    private static final char ESCAPE_SIGN = '/';
    /**
     * Set of omission an escape signs in use.
     */
    private static final Set<Integer> OMISSION_SIGNS = (OMISSION_OPEN_SIGN + OMISSION_CLOSE_SIGN + ESCAPE_SIGN).chars().boxed().collect(Collectors.toSet());
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
        this.textWithOmissions = ""; // clear
        this.omissionsList.clear(); // clear
        int startSubstring = 0;
        int endSubstring = 0;
        boolean whichSign = true; // true: awaiting omission open sign, false: awaiting omission close sign
        String sign = OMISSION_OPEN_SIGN; // first omission sign is omission open sign
        String buildString = ""; // saves text parts temporarily
        while (endSubstring < text.length()) { // while not at end of string
            if (text.charAt(endSubstring) == sign.charAt(0) && // if found omission sign
                    text.substring(endSubstring, endSubstring + sign.length()).equals(sign)) {
                if (whichSign) { // found omission open sign -> end of normal text part
                    buildString += text.substring(startSubstring, endSubstring + sign.length()); // save rest of normal text part + omission open sign temporarily
                    this.textWithOmissions += buildString; // save normal text part since last omission close sign
                    startSubstring = endSubstring + sign.length(); // next omission part starts after omission open sign
                    sign = OMISSION_CLOSE_SIGN; // next omission sign is omission close sign
                } else { // found omssion close sign -> end of omission part
                    buildString += text.substring(startSubstring, endSubstring); // save rest of omission part temporarily
                    this.omissionsList.add(buildString); // save omission part since last omssion open sign
                    startSubstring = endSubstring; // next normal text part starts here
                    sign = OMISSION_OPEN_SIGN; // next omission sign is omission open sign
                }
                buildString = ""; // clear temp string
                whichSign = !whichSign; // awaiting the other omission sign
                endSubstring += sign.length(); // jump over last omission sign
            } else if (text.charAt(endSubstring) == ESCAPE_SIGN && endSubstring + 1 < text.length() &&
                    OMISSION_SIGNS.contains((int) text.charAt(endSubstring + 1))) { // if found escape sign followed by any omission or escape sign
                buildString += text.substring(startSubstring, endSubstring); // save part temporarily since last omission sign
                buildString += text.substring(endSubstring + 1, endSubstring + 2); // jump over escape sign and save escaped character temporarily
                startSubstring = endSubstring + 2; // next part starts after escaped character
                endSubstring += 2; // nextpart starts after escaped character
            } else // if not found an omission sign or an escape sign
                endSubstring++; // check next character
        }
        this.textWithOmissions += buildString; //save rest
        this.textWithOmissions += text.substring(startSubstring, text.length()); // save rest
    }
}
