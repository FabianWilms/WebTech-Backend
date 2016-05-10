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
 * A topic describes what subjects an {@link Exercise} covers. A question can have multiple topics, for example "java" and "math".
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

    public Topic() {}

    public Topic(String name) {
        this.setName(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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
