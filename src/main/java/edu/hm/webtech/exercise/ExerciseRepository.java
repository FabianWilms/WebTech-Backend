package edu.hm.webtech.exercise;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of {@link Exercise}.
 * 
 * @author Bianca
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    
    /**
     * Find all Exercises with topic id from {@link TopicBloomLevel}.
     * 
     * @param topicId topic id of TopicBloomLevel
     * @return all matched exercises
     */
    List<Exercise> findByTopicBloomLevelTopicId(final Long topicId);
}
