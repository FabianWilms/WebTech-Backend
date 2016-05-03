package edu.hm.webtech.repositories;

import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.entities.Exercise;
import edu.hm.webtech.entities.MultipleChoice;
import edu.hm.webtech.entities.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Bianca
 */
@Repository
public interface MultipleChoiceRepository extends CrudRepository<MultipleChoice, Long> {}
