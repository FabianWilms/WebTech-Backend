package edu.hm.webtech.exercise;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bianca
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    
    List<Exercise> findByTopicBloomLevelTopicId(Long topicId);

}
