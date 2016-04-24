package edu.hm.webtec;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents difficulties for {@link Topic Topics} and {@link Exercise Exercises}.
 * 
 * @author Bianca
 */
public enum BloomLevel {
    ERINNERN(1), VERSTEHEN(2), ANWENDEN(3), ANALYSIEREN(4), BEWERTEN(5), KREIEREN(6);
    
    private final int value;
    
    BloomLevel(int value) {
        this.value = value; 
    }

    public int getValue() {
        return value;
    }
    
    /**
     * Gets the {@link Optional} from type {@link BloomLevel} which matches the associated {@code value}.
     * @param value the value to find the bloom level
     * @return the optional from type bloom level
     */
    public Optional<BloomLevel> fromValue(final int value) {
        return Arrays.asList(values()).stream().filter(bloomLevel -> bloomLevel.getValue() == value).findFirst();
    }
}
