package edu.hm.webtec.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Fabian on 09.04.2016.
 */
public class BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
