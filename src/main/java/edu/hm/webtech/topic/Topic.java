package edu.hm.webtech.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.hm.webtech.exercise.Exercise;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * A {@link Topic} describes what subjects an {@link Exercise} covers. A question can have multiple topics, for example "java" and "math".
 */
@Entity
public class Topic implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    /**
     * The topics name. Only one topic with the same name can exist.
     */
    @NotEmpty
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @Column(unique = true)
    private String lowercaseName;

    /**
     * Create a new {@link Topic}. This constructor should not be used. It's purpose is solely for JPA.
     */
    public Topic() {
        //JPA only
    }

    /**
     * Creates a new {@link Topic}.
     * 
     * @param name the name of the topic
     */
    public Topic(String name) {
        this.setName(name);
    }

    /**
     * Gets the id of the {@link Topic}.
     * 
     * @return the id of the topic
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the {@link Topic}.
     * 
     * @param id the id of the topic
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the {@link Topic}.
     * 
     * @return the name of the topic
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Topic}.
     * 
     * @param name the name of the topic
     */
    public void setName(String name) {
        this.name = name;
        this.lowercaseName = name.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic1 = (Topic) o;

        if (getId() != topic1.getId()) return false;
        return getName() != null ? getName().equals(topic1.getName()) : topic1.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
