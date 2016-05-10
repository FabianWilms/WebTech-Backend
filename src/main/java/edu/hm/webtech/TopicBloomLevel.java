package edu.hm.webtech;

import edu.hm.webtech.entities.Topic;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
/**
 * An TopicBloomLevel is responisble to assign a {@link Topic} to a {@link BloomLevel}.
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

    public TopicBloomLevel(){}

    public TopicBloomLevel(BloomLevel bloomLevel, long topicId){
        this.setBloomLevel(bloomLevel);
        this.setTopicId(topicId);
    }

    public BloomLevel getBloomLevel() {
        return bloomLevel;
    }

    public void setBloomLevel(BloomLevel bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
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
