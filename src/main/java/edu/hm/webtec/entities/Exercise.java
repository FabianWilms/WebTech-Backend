package edu.hm.webtec.entities;

import com.sun.istack.internal.NotNull;
import edu.hm.webtec.BloomLevel;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * A Exercise contains the mapping between {@link Topic} and {@link BloomLevel}.
 * 
 * @author Bianca
 */

public abstract class Exercise implements Serializable {
    
    @Id
    @NotNull
    private static final long serialVersionUID = 1L;
    
    @NotEmpty
    private String description;
    
    @NotNull
    private Map<Topic, BloomLevel> topics;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Topic, BloomLevel> getTopics() {
        return topics;
    }

    public void setTopics(Map<Topic, BloomLevel> topics) {
        this.topics = topics;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.topics);
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
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.topics, other.topics)) {
            return false;
        }
        return true;
    }
}
