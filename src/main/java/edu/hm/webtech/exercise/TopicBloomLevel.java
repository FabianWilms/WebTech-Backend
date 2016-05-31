package edu.hm.webtech.exercise;

import edu.hm.webtech.topic.Topic;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * An {@link TopicBloomLevel} is responisble to assign a {@link Topic} to a {@link BloomLevel}.
 *
 * @author Bianca
 */
@Embeddable
public class TopicBloomLevel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private BloomLevel bloomLevel; 
    
    @NotNull
    private long topicId;

    /**
     * Create a new {@link TopicBloomLevel}. This constructor should not be used. It's purpose is solely for JPA.
     */
    public TopicBloomLevel(){
        //JPA only
    }

    /**
     * Creates a new {@link TopicBloomLevel}.
     * 
     * @param bloomLevel the bloom level
     * @param topicId id of the topic
     */
    public TopicBloomLevel(final BloomLevel bloomLevel, final long topicId){
        this.setBloomLevel(bloomLevel);
        this.setTopicId(topicId);
    }

    /**
     * Gets the {@link BloomLevel}.
     * 
     * @return bloomLevel
     */
    public BloomLevel getBloomLevel() {
        return bloomLevel;
    }

    /**
     * Sets the {@link BloomLevel}.
     * 
     * @param bloomLevel bloomLevel
     */
    public void setBloomLevel(final BloomLevel bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    /**
     * Gets the id of the {@link Topic}.
     * 
     * @return id of the topic
     */
    public long getTopicId() {
        return topicId;
    }

    /**
     * Sets the id of the {@link Topic}.
     * 
     * @param topicId  id of the topic
     */
    public void setTopicId(final long topicId) {
        this.topicId = topicId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.bloomLevel);
        hash = 29 * hash + (int) (this.topicId ^ (this.topicId >>> 32));
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
        final TopicBloomLevel other = (TopicBloomLevel) obj;
        if (this.topicId != other.topicId) {
            return false;
        }
        if (this.bloomLevel != other.bloomLevel) {
            return false;
        }
        return true;
    }
}
