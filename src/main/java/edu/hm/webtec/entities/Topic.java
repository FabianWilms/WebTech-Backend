package edu.hm.webtec.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Fabian on 21.04.2016.
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue
    private long id;

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
