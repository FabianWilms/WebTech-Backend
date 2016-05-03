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
    private Topic topic;

    public TopicBloomLevel() {}
    
    public TopicBloomLevel(BloomLevel bloomLevel, Topic topic) {
        this.setBloomLevel(bloomLevel);
        this.setTopic(topic);
    }
    
    public BloomLevel getBloomLevel() {
        return bloomLevel;
    }

    public void setBloomLevel(BloomLevel bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.bloomLevel);
        hash = 47 * hash + Objects.hashCode(this.topic);
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
        if (this.bloomLevel != other.bloomLevel) {
            return false;
        }
        if (!Objects.equals(this.topic, other.topic)) {
            return false;
        }
        return true;
    }
    
    
}
