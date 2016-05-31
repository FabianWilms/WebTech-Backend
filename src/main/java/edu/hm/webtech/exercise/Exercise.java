package edu.hm.webtech.exercise;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import edu.hm.webtech.topic.Topic;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * An Exercise contains a description and a pair of {@link BloomLevel} and {@link Topic} which is described in {@link TopicBloomLevel}.
 * 
 * @author Bianca
 */
@Entity
public abstract class Exercise implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;
    
    @NotEmpty
    private String description;
    
    @Embedded
    @ElementCollection
    @CollectionTable(name="exercise_topicBloomLevel")
    private Set<TopicBloomLevel> topicBloomLevel = new HashSet<>();
    
    /**
     * Gets the description.
     * 
     * @return description for the exercise
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description description for the exercise
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the Topic BloomLevel combination.
     * 
     * @return the Topic BloomLevel combination.
     */
    public Set<TopicBloomLevel> getTopicBloomLevel() {
        return topicBloomLevel;
    }

    /**
     * Sets the Topic BloomLevel combination.
     * 
     * @param topicBloomLevel the Topic BloomLevel combination
     */
    public void setTopicBloomLevel(final Set<TopicBloomLevel> topicBloomLevel) {
        this.topicBloomLevel = topicBloomLevel;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id. 
     * 
     * @param id the id
     */
    public void setId(final long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.topicBloomLevel);
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
        final Exercise other = (Exercise) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.topicBloomLevel, other.topicBloomLevel);
    }
}