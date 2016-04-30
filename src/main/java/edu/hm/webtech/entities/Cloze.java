package edu.hm.webtech.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Marko on 24.04.16.
 */
@Entity
public class Cloze extends Exercise {

    @NotEmpty
    @Column
    private String text;
    @Column
    private String textWithOmissions;
    @ElementCollection
    private List<String> omissionsList;

    public String getText() {
        return text;
    }

    public List<String> getOmissionsList() {
        return omissionsList;
    }

    public String getTextWithOmissions() {
        return textWithOmissions;
    }

    public List<String> getOmissionsListUnordered() {
        List<String> result = omissionsList;
        Collections.shuffle(result);
        return result;
    }

    public void setText(final String text) {
        this.text = text;
        this.textWithOmissions = generateTextWithOmissions(text);
        this.omissionsList = generateOmissionsList(text);
    }

    private String generateTextWithOmissions(final String text) {
        String result = "";
        final StringIterator strIt = new StringIterator(text, '#');
        boolean ticktock = true;
        while (strIt.hasNext()) {
            if (ticktock)
                result += strIt.next();
            else {
                strIt.next();
                result += "<...>";
            }
            ticktock = !ticktock;
        }
        return result;
    }

    private List<String> generateOmissionsList(final String text) {
        final List<String> result = new ArrayList<>();
        final StringIterator strIt = new StringIterator(text, '#');
        boolean ticktock = false;
        while (strIt.hasNext()) {
            if (ticktock)
                result.add(strIt.next());
            else
                strIt.next();
            ticktock = !ticktock;
        }

        return result;
    }

    private class StringIterator implements Iterator<String> {
        private String strToIterateOver;
        private final char separator;

        StringIterator(final String string, final char separator) {
            this.strToIterateOver = string;
            this.separator = separator;
        }

        @Override
        public boolean hasNext() {
            return strToIterateOver.length() > 0;
        }

        @Override
        public String next() {

            String returnString = "";

            if (hasNext()) {

                int posOfSeparator = 0;
                while (posOfSeparator < strToIterateOver.length()
                        && separator != strToIterateOver
                        .charAt(posOfSeparator)) {
                    posOfSeparator++;
                }
                if (posOfSeparator == strToIterateOver.length()) {
                    returnString = strToIterateOver;
                    strToIterateOver = "";
                } else {
                    returnString = strToIterateOver
                            .substring(0, posOfSeparator);
                    strToIterateOver = strToIterateOver.substring(
                            posOfSeparator + 1, strToIterateOver.length());
                }
            }
            //strToIterateOver = strToIterateOver.trim();
            return returnString;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
