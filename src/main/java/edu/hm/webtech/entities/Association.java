package edu.hm.webtech.entities;

import edu.hm.webtech.TopicBloomLevel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author peter-mueller
 */
@Entity
public class Association extends Exercise {

    @ElementCollection(targetClass = HashSet.class)
    @NotEmpty
    private Map<String, Set<String>> associations = new HashMap<>();

    public Association(String description, Set<TopicBloomLevel> topicBloomLevel, Map<String, Set<String>> associations) {
        super();
        setDescription(description);
        setTopicBloomLevel(topicBloomLevel);
        this.associations = associations;
    }

    public Association() {
        //JPA only
    }

    public Map<String, Set<String>> getAssociations() {
        return associations;
    }

    public void setAssociations(Map<String, Set<String>> associations) {
        this.associations = associations;
    }
}
