package edu.hm.webtech.entities;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


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
    private String topic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic1 = (Topic) o;

        if (getId() != topic1.getId()) return false;
        return getTopic().equals(topic1.getTopic());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getTopic().hashCode();
        return result;
    }
}
