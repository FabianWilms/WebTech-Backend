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
 * Lueckentext mit geordneter und gemischter Liste von Antworten. Ein auszulassender Textabschnitt wird zwischen zwei
 * # eingeklammert.
 *
 * @author M. Streich
 * @version 01.05.2016
 */
@Entity
public class Cloze extends Exercise {

    /**
     * Klartext mit auszulassenden Woertern.
     */
    @NotEmpty
    @Column
    private String text;
    /**
     * Text mit Luecken.
     */
    @Column
    private String textWithOmissions;
    /**
     * Loesungswoerter in ihrer Reihenfolge des Aufgebentextes.
     */
    @ElementCollection
    private List<String> omissionsList;

    public String getText() {
        return text;
    }

    /**
     * Geordnete Loesungswoerter.
     *
     * @return Geordnete Loesungswoerter.
     */
    public List<String> getOmissionsList() {
        return omissionsList;
    }

    /**
     * Text mit Luecken. Luecken markiert mit <...>.
     *
     * @return Text mit Luecken.
     */
    public String getTextWithOmissions() {
        return textWithOmissions;
    }

    /**
     * Ungeordente Loesungswoerter.
     *
     * @return Ungeordnete Loesungswoerter.
     */
    public List<String> getOmissionsListUnordered() {
        List<String> result = omissionsList;
        Collections.shuffle(result);
        return result;
    }

    /**
     * Setter Fragentext. Ein auszulassender Textabschnitt wird zwischen zwei '#' eingeklammert.
     * Beispiel: "Die virtuelle Maschine von Java ist eine #Stackmaschine#."
     *
     * @param text Zu speichernder Text.
     */
    public void setText(final String text) {
        this.text = text;
        this.textWithOmissions = generateTextWithOmissions(text);
        this.omissionsList = generateOmissionsList(text);
    }

    /**
     * Text mit Luecken generieren.
     *
     * @param text Text ohne Luecken mit Trennzeichen '#'.
     * @return Text mit Luecken.
     */
    private String generateTextWithOmissions(final String text) {
        String result = "";
        final StringIterator strIt = new StringIterator(text, '#');
        /* Jeder zweite Abschnitt wird mit <...> ersetzt. */
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

    /**
     * Lister der Loesungswoerter
     *
     * @param text Text ohne Luecken mit Trennzeichen '#'.
     * @return Lister der Loesungswoerter.
     */
    private List<String> generateOmissionsList(final String text) {
        final List<String> result = new ArrayList<>();
        final StringIterator strIt = new StringIterator(text, '#');
        /* Jeder zweite Absschnitt wird als Loesungswort in die Liste getan. */
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

    /**
     * Iterator ueber Strings. Iteriert ueber Abschnitte des Strings, die mit einem angegebenen Trennzeichen
     * gegliedert sind.
     */
    private class StringIterator implements Iterator<String> {
        /**
         * String ueber den iteriert wird.
         */
        private String strToIterateOver;
        /**
         * Trennzeichen des Iterators.
         */
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
                // TODO Sperrzeichen fuer Trennzeichen
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
