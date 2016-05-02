package edu.hm.webtech.repositories;

import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.entities.Exercise;
import edu.hm.webtech.entities.Topic;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bianca
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    
    List<Exercise> findByTopicBloomLevelTopic(Topic topic);
}
