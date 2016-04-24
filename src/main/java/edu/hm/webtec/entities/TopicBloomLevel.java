package edu.hm.webtec.entities;

import edu.hm.webtec.BloomLevel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * An TopicBloomLevel is responisble to assign a {@link Topic} to a {@link BloomLevel}.
 *
 * @author Bianca
 */
@Entity
public class TopicBloomLevel implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EXERCISE_ID")
    private Exercise exercise;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private BloomLevel bloomLevel;
    
    @NotNull
    @OneToOne
    private Topic topic;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BloomLevel getBloomLevel() {
        return bloomLevel;
    }

    public void setBloomLevel(BloomLevel bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.exercise);
        hash = 59 * hash + Objects.hashCode(this.bloomLevel);
        hash = 59 * hash + Objects.hashCode(this.topic);
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
        final TopicBloomLevel other = (TopicBloomLevel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.exercise, other.exercise)) {
            return false;
        }
        if (this.bloomLevel != other.bloomLevel) {
            return false;
        }
        if (!Objects.equals(this.topic, other.topic)) {
            return false;
        }
        return true;
    }
}
