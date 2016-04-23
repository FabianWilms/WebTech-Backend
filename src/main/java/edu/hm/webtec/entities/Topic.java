package edu.hm.webtec.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * A topic describes what subjects a question covers. A question can have multiple topics, for example "java" and "math".
 */
@Entity
public class Topic {

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
}
