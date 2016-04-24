package edu.hm.webtec.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
    
    @NotNull
    @OneToMany(mappedBy="exercise")
    private List<TopicBloomLevel> topicBloomLevel;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TopicBloomLevel> getTopicBloomLevel() {
        return topicBloomLevel;
    }

    public void setTopicBloomLevel(List<TopicBloomLevel> topicBloomLevel) {
        this.topicBloomLevel = topicBloomLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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