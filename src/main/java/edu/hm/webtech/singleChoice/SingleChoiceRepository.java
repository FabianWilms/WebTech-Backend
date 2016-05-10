package edu.hm.webtech.singleChoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bianca
 */
@Repository
public interface SingleChoiceRepository extends CrudRepository<SingleChoice, Long> {}
