package edu.hm.webtec.repositories;

import edu.hm.webtec.TopicBloomLevel;
import edu.hm.webtec.entities.Exercise;
import edu.hm.webtec.entities.Topic;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bianca
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, TopicBloomLevel> {
    
    List<Exercise> findByTopicBloomLevelTopic(Topic topic);
}
