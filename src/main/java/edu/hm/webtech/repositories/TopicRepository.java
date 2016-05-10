package edu.hm.webtech.repositories;

import edu.hm.webtech.entities.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    Topic findTopicByName(String name);

}
