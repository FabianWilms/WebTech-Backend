package edu.hm.webtech.topic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of the entity {@link Topic}.
 * 
 * @author Bianca
 */
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    /**
     * Find a {@link Topic} with the associated {@code name}.
     * 
     * @param name the name of the topic
     * @return the matched topic
     */
    Topic findTopicByName(String name);
}
