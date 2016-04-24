package edu.hm.webtec.repositories;

import edu.hm.webtec.BloomLevel;
import edu.hm.webtec.entities.Exercise;
import edu.hm.webtec.entities.Topic;
import edu.hm.webtec.entities.TopicBloomLevel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bianca
 */
@Repository
public interface TopicBloomLevelRepository extends CrudRepository<TopicBloomLevel, Long> {
    
    Exercise findByTopicAndBloomLevel(Topic topic, BloomLevel bloomLevel);
    
    List<Exercise> findByTopic(Topic topic);
}
