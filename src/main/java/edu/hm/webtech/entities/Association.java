package edu.hm.webtech.entities;

import edu.hm.webtech.TopicBloomLevel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author peter-mueller
 */
@Entity
public class Association extends Exercise {

    @ElementCollection
    @NotEmpty
    private Map<String,String[]> associations = new HashMap<>();

    public Association(String description, Set<TopicBloomLevel> topicBloomLevel, Map<String,String[]> associations) {
        super();
        setDescription(description);
        setTopicBloomLevel(topicBloomLevel);
        this.associations = associations;
    }

    public Association() {
        //JPA only
    }

    public Map<String, String[]> getAssociations() {
        return associations;
    }

    public void setAssociations(Map<String, String[]> associations) {
        this.associations = associations;
    }
}
