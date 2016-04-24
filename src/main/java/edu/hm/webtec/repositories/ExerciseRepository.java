package edu.hm.webtec.repositories;

import edu.hm.webtec.entities.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bianca
 */
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {}
