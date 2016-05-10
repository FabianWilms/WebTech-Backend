package edu.hm.webtech.multipleChoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bianca
 */
@Repository
public interface MultipleChoiceRepository extends CrudRepository<MultipleChoice, Long> {}
